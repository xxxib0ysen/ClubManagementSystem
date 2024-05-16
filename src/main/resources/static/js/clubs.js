function loadClubs(pageNumber = 1, searchTerm = '') {
    var htmlContent = `
    <div class="row mb-3">
        <div class="col">
            <input type="text" class="form-control" id="searchClubInput" placeholder="搜索社团名称">
        </div>
        <div class="col-auto">
            <button class="btn btn-primary" onclick="searchClubs()">搜索</button>
            <button class="btn btn-secondary" onclick="resetClubSearch()">重置</button>
        </div>
    </div>
    <div class="mb-3">
        <button class="btn btn-success" id="addClubButton">新增社团</button>
        <button class="btn btn-danger" onclick="batchDeleteClubs()">批量删除</button>
    </div>
    <table class="table">
        <thead>
            <tr>
                <th scope="col"><input type="checkbox" id="selectAllClubs" onclick="toggleSelectAll(this)"></th>
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
    </table>
    <nav>
        <ul class="pagination" id="pagination">
            <!-- 分页按钮将通过JavaScript动态加载 -->
        </ul>
    </nav>`;
    $('#content').html(htmlContent);
    loadClubData(pageNumber, searchTerm); // 调用函数加载并展示社团数据

    // 绑定新增社团按钮的点击事件
    $('#addClubButton').on('click', function() {
        loadAddClubForm();
    });

    // 预填搜索框中的搜索条件
    $('#searchClubInput').val(searchTerm);
}

function loadClubData(pageNumber = 1, searchTerm = '') {
    $.ajax({
        url: '/clubs/page',
        method: 'GET',
        data: {
            page: pageNumber,
            size: 5,
            club_name: searchTerm // 将搜索条件添加到请求参数中
        },
        success: function(response) {
            console.log("Response data:", response); // 调试输出
            const tbody = $('#clubListTable');
            tbody.empty();

            response.list.forEach(club => {
                tbody.append(`
                <tr>
                    <td><input type="checkbox" class="clubCheckbox" value="${club.club_id}"></td>
                    <td>${club.club_id}</td>
                    <td><img src="${club.image_url}" alt="${club.club_name}" style="width: 100px;"></td>
                    <td>${club.club_name}</td>
                    <td>${club.description}</td>
                    <td>
                        <button class="btn btn-info" onclick="loadEditClubForm(${club.club_id})">编辑</button>
                        <button class="btn btn-danger" onclick="deleteClub(${club.club_id})">删除</button>
                    </td>
                </tr>
                `);
            });

            // 分页处理
            const pagination = $('#pagination');
            pagination.empty();
            for (let i = 1; i <= response.pages; i++) {
                pagination.append(`<li class="page-item ${i === pageNumber ? 'active' : ''}">
                    <a class="page-link" href="#" onclick="loadClubs(${i}, '${searchTerm}')">${i}</a>
                </li>`);
            }
        },
        error: function() {
            $('#clubListTable').html('<tr><td colspan="6">加载数据失败，请稍后重试。</td></tr>');
        }
    });
}

function searchClubs() {
    loadClubs(1, $('#searchClubInput').val().trim()); // 重新加载数据，从第一页开始，并传递搜索条件
}


function resetClubSearch() {
    $('#searchClubInput').val(''); // 清空搜索输入框
    loadClubs(); // 重新加载数据，从第一页开始，并且没有搜索条件
}



function toggleSelectAll(selectAllCheckbox) {
    const checkboxes = $('.clubCheckbox');
    checkboxes.prop('checked', selectAllCheckbox.checked);
}

function loadEditClubForm(club_id) {
    $.ajax({
        url: '/clubs/' + club_id,
        method: 'GET',
        success: function(club) {
            $('#content').html(`
            <h2>编辑社团</h2>
            <form id="editClubForm" enctype="multipart/form-data">
                <input type="hidden" id="club_id" name="club_id" value="${club.club_id}">
                <div class="form-group">
                    <label for="club_name">社团名称</label>
                    <input type="text" class="form-control" id="club_name" name="club_name" value="${club.club_name}" required>
                </div>
                <div class="form-group">
                    <label for="description">描述</label>
                    <textarea class="form-control" id="description" name="description" rows="3">${club.description}</textarea>
                </div>
                <div class="form-group">
                    <label for="image_url">社团图片</label>
                    <input type="file" class="form-control-file" id="image_url" name="image_url">
                    <img src="${club.image_url}" alt="${club.club_name}" style="width: 100px; margin-top: 10px;">
                </div>
                <button type="submit" class="btn btn-primary">保存</button>
            </form>
            `);

            $('#editClubForm').submit(function(e) {
                e.preventDefault(); // 阻止默认的表单提交行为

                var formData = new FormData(this); // 创建 FormData 对象来上传文件
                formData.append('club_id', $('#club_id').val());

                $.ajax({
                    url: '/clubs/' + $('#club_id').val(),
                    method: 'PUT',
                    data: formData,
                    processData: false, // 不处理数据
                    contentType: false, // 不设置内容类型
                    success: function(response) {
                        alert('社团信息更新成功！');
                        loadClubs(); // 更新成功后重新加载社团列表
                    },
                    error: function(xhr, status, error) {
                        alert('社团信息更新失败，请稍后再试。');
                        console.error(xhr.responseText);
                    }
                });
            });
        },
        error: function() {
            alert('获取社团信息失败，请稍后再试。');
        }
    });
}
function deleteClub(club_id) {
    if (confirm('确定要删除这个社团吗？')) {
        $.ajax({
            url: '/clubs/' + club_id,
            method: 'DELETE',
            success: function() {
                alert('社团删除成功！');
                loadClubs(); // 重新加载社团数据
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
                loadClubs(); // 重新加载社团数据
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
    <form id="addClubForm" enctype="multipart/form-data">
        <div class="form-group">
            <label for="club_name">社团名称</label>
            <input type="text" class="form-control" id="club_name" name="club_name" required>
        </div>
        <div class="form-group">
            <label for="description">描述</label>
            <textarea class="form-control" id="description" name="description" rows="3"></textarea>
        </div>
        <div class="form-group">
            <label for="image_url">社团图片</label>
            <input type="file" class="form-control-file" id="image_url" name="image_url">
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