function loadClubs() {
    var htmlContent = `
    <div class="row mb-3">
        <div class="col">
            <input type="text" class="form-control" id="searchClubInput" placeholder="搜索社团">
        </div>
        <div class="col-auto">
            <button class="btn btn-primary" onclick="searchClubs()">搜索</button>
        </div>
    </div>
    <div class="mb-3">
        <button class="btn btn-success" id="addClubButton">新增社团</button>
        <button class="btn btn-danger" onclick="batchDeleteClubs()">批量删除</button>
    </div>
    <table class="table">
        <thead>
            <tr>
                <th scope="col"><input type="checkbox" id="selectAllClubs"></th>
                <th scope="col">编号</th>
                <th scope="col">图片</th>
                <th scope="col">社团名称</th>
                <th scope="col">描述</th>
                <th scope="col">操作</th>
            </tr>
        </thead>
        <tbody id="clubListTable">
            <!-- 社团数据将通过JavaScript动态加载 -->
        </tbody>
    </table>`;
    $('#content').html(htmlContent);
    loadClubData(); // 调用函数加载并展示社团数据

    // 绑定新增社团按钮的点击事件
    $('#addClubButton').on('click', function() {
        updateContent('addClub');
    });
}

function loadClubData(searchTerm = '') {
    $.ajax({
        url: '/clubs/', // 根据你的后端API路径调整
        method: 'GET',
        data: { term: searchTerm }, // 将搜索词作为查询参数发送
        success: function(data) {
            const tbody = $('#clubListTable');
            tbody.empty();
            data.forEach(club => {
                tbody.append(`
                <tr>
                    <td><input type="checkbox" class="clubCheckbox" value="${club.club_id}"></td>
                    <td>${club.club_id}</td>
                    <td><img src="${club.image_url}" alt="${club.club_name}" style="width: 100px;"></td>
                    <td>${club.club_name}</td>
                    <td>${club.description}</td>
                    <td>
                        <button class="btn btn-info" onclick="editClub(${club.club_id})">编辑</button>
                        <button class="btn btn-danger" onclick="deleteClub(${club.club_id})">删除</button>
                    </td>
                </tr>
            `);
            });
        },
        error: function() {
            $('#clubListTable').html('<tr><td colspan="6">加载数据失败，请稍后重试。</td></tr>');
        }
    });
}

function searchClubs() {
    var searchTerm = $('#searchClubInput').val().trim(); // 获取输入值并去除首尾空白
    loadClubData(searchTerm); // 将搜索词传递给加载数据的函数
}

// 创建编辑社团模态框的HTML和JavaScript代码
var editClubModalHtml = `
<!-- 编辑社团模态框 -->
<div class="modal fade" id="editClubModal" tabindex="-1" role="dialog" aria-labelledby="editClubModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editClubModalLabel">编辑社团信息</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="editClubForm">
                    <input type="hidden" id="club_id" name="club_id">
                    <div class="form-group">
                        <label for="clubName">社团名称</label>
                        <input type="text" class="form-control" id="clubName" name="clubName" required>
                    </div>
                    <div class="form-group">
                        <label for="description">描述</label>
                        <textarea class="form-control" id="description" name="description" rows="3"></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">保存</button>
                </form>
            </div>
        </div>
    </div>
</div>
`;

// 将编辑社团模态框插入到页面中
$('body').append(editClubModalHtml);

// 编辑社团功能
function editClub(club_id) {
    // 发送Ajax请求获取特定ID的社团信息
    $.ajax({
        url: '/clubs/' + club_id,
        method: 'GET',
        success: function(club) {
            // 将社团信息填充到模态框中
            $('#editClubModal #club_id').val(club.club_id);
            $('#editClubModal #clubName').val(club.club_name);
            $('#editClubModal #description').val(club.description);
            $('#editClubModal').modal('show'); // 显示模态框
        },
        error: function() {
            alert('获取社团信息失败，请稍后再试。');
        }
    });
}

// 监听编辑表单的提交事件
$('#editClubForm').submit(function(e) {
    e.preventDefault(); // 阻止表单默认提交行为

    var formData = $(this).serialize(); // 获取表单数据

    // 发送Ajax请求更新社团信息
    $.ajax({
        url: '/clubs/' + $('#club_id').val(),
        method: 'PUT',
        data: formData,
        success: function() {
            alert('社团信息更新成功！');
            $('#editClubModal').modal('hide'); // 隐藏模态框
            loadClubData(); // 重新加载社团数据
        },
        error: function() {
            alert('社团信息更新失败，请稍后再试。');
        }
    });
});

function deleteClub(club_id) {
    if (confirm('确定要删除这个社团吗？')) {
        $.ajax({
            url: '/clubs/' + club_id,
            method: 'DELETE',
            success: function() {
                alert('社团删除成功！');
                loadClubData(); // 重新加载社团数据
            },
            error: function() {
                alert('删除失败，请稍后再试。');
            }
        });
    }
}

function batchDeleteClubs() {
    var selectedclub_ids = $('.clubCheckbox:checked').map(function() { return $(this).val(); }).get();
    if (selectedclub_ids.length === 0) {
        alert('请选择要删除的社团。');
        return;
    }
    if (confirm('确定要删除选中的社团吗？')) {
        $.ajax({
            url: '/clubs/batch-delete',
            method: 'DELETE',
            data: JSON.stringify(selectedclub_ids),
            contentType: 'application/json',
            success: function() {
                alert('批量删除社团成功！');
                loadClubData(); // 重新加载社团数据
            },
            error: function() {
                alert('批量删除失败，请稍后再试。');
            }
        });
    }
}

function loadAddClubForm() {
    $('#content').html(`
    <h2>添加社团</h2>
    <form id="addClubForm">
        <div class="form-group">
            <label for="clubName">社团名称</label>
            <input type="text" class="form-control" id="clubName" name="clubName" required>
        </div>
        <div class="form-group">
            <label for="description">描述</label>
            <textarea class="form-control" id="description" name="description" rows="3"></textarea>
        </div>
        <div class="form-group">
            <label for="clubImage">社团图片</label>
            <input type="file" class="form-control-file" id="clubImage" name="clubImage">
        </div>
        <button type="submit" class="btn btn-primary">提交</button>
    </form>
`);

    $('#addClubForm').submit(function(e) {
        e.preventDefault(); // 阻止默认的表单提交行为

        var formData = new FormData(this); // 创建 FormData 对象来上传文件
        $.ajax({
            url: '/clubs/',
            method: 'POST',
            data: formData,
            processData: false, // 不处理数据
            contentType: false, // 不设置内容类型
            success: function(response) {
                alert('社团添加成功！');
                loadClubs(); // 添加成功后重新加载社团列表
            },
            error: function(xhr, status, error) {
                alert('添加社团失败，请稍后重试。');
                console.error(xhr.responseText);
            }
        });
    });
}
