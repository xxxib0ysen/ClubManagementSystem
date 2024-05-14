function loadMembers(clubId = null) {
    var htmlContent = `
    <div class="row mb-3">
        <div class="col">
            <input type="text" class="form-control" id="searchMemberInput" placeholder="搜索会员">
        </div>
        <div class="col-auto">
            <button class="btn btn-primary" onclick="searchMembers()">搜索</button>
        </div>
    </div>
    <div class="mb-3">
        <button class="btn btn-success" id="addMemberButton">新增会员</button>
        <button class="btn btn-danger" onclick="batchDeleteMembers()">批量删除</button>
    </div>
    <table class="table">
        <thead>
            <tr>
                <th scope="col"><input type="checkbox" id="selectAllMembers"></th>
                <th scope="col">成员ID</th>
                <th scope="col">成员名</th>
                <th scope="col">所属社团</th>
                <th scope="col">操作</th>
            </tr>
        </thead>
        <tbody id="memberListTable">
            <!-- 会员数据将通过JavaScript动态加载 -->
        </tbody>
    </table>`;
    $('#content').html(htmlContent);
    loadMemberData(clubId); // 调用函数加载并展示会员数据

    // 绑定新增会员按钮的点击事件
    $('#addMemberButton').on('click', function() {
        updateContent('addMember');
    });
}

function loadMemberData(clubId = null, searchTerm = '') {
    let url = clubId ? `/members/by-club/${clubId}` : '/members/';
    let data = clubId ? {} : { term: searchTerm };
    $.ajax({
        url: url,
        method: 'GET',
        data: data, // 将搜索词作为查询参数发送
        success: function(data) {
            const tbody = $('#memberListTable');
            tbody.empty();
            data.forEach(member => {
                tbody.append(`
                <tr>
                    <td><input type="checkbox" class="memberCheckbox" value="${member.member_id}"></td>
                    <td>${member.member_id}</td>
                    <td>${member.member_name}</td>
                    <td>${member.club_id}</td>
                    <td>
                        <button class="btn btn-info" onclick="editMember(${member.member_id})">编辑</button>
                        <button class="btn btn-danger" onclick="deleteMember(${member.member_id})">删除</button>
                    </td>
                </tr>
            `);
            });
        },
        error: function() {
            $('#memberListTable').html('<tr><td colspan="5">加载数据失败，请稍后重试。</td></tr>');
        }
    });
}

function searchMembers() {
    var searchTerm = $('#searchMemberInput').val().trim(); // 获取输入值并去除首尾空白
    loadMemberData(null, searchTerm); // 将搜索词传递给加载数据的函数
}

function batchDeleteMembers() {
    var selectedMemberIds = $('.memberCheckbox:checked').map(function() { return $(this).val(); }).get();
    if (selectedMemberIds.length === 0) {
        alert('请选择要删除的会员。');
        return;
    }
    if (confirm('确定要删除选中的会员吗？')) {
        $.ajax({
            url: '/members/batch-delete',
            method: 'DELETE',
            data: JSON.stringify(selectedMemberIds),
            contentType: 'application/json',
            success: function() {
                alert('批量删除会员成功！');
                loadMemberData(); // 重新加载会员数据
            },
            error: function() {
                alert('批量删除失败，请稍后再试。');
            }
        });
    }
}

function loadAddMemberForm() {
    $('#content').html(`
    <h2>添加会员</h2>
    <form id="addMemberForm">
        <div class="form-group">
            <label for="memberName">会员名称</label>
            <input type="text" class="form-control" id="memberName" name="memberName" required>
        </div>
        <div class="form-group">
            <label for="clubId">所属社团ID</label>
            <input type="text" class="form-control" id="clubId" name="clubId" required>
        </div>
        <button type="submit" class="btn btn-primary">提交</button>
    </form>
`);

    $('#addMemberForm').submit(function(e) {
        e.preventDefault(); // 阻止默认的表单提交行为

        var formData = $(this).serialize(); // 获取表单数据

        $.ajax({
            url: '/members/',
            method: 'POST',
            data: formData,
            success: function(response) {
                alert('会员添加成功！');
                loadMembers(); // 添加成功后重新加载会员列表
            },
            error: function(xhr, status, error) {
                alert('添加会员失败，请稍后重试。');
                console.error(xhr.responseText);
            }
        });
    });
}

function editMember(memberId) {
    // 显示编辑会员表单
    $.ajax({
        url: '/members/' + memberId,
        method: 'GET',
        success: function(member) {
            $('#content').html(`
            <h2>编辑会员</h2>
            <form id="editMemberForm">
                <input type="hidden" id="member_id" name="member_id" value="${member.member_id}">
                <div class="form-group">
                    <label for="memberName">会员名称</label>
                    <input type="text" class="form-control" id="memberName" name="memberName" value="${member.member_name}" required>
                </div>
                <div class="form-group">
                    <label for="clubId">所属社团ID</label>
                    <input type="text" class="form-control" id="clubId" name="clubId" value="${member.club_id}" required>
                </div>
                <button type="submit" class="btn btn-primary">保存</button>
            </form>
            `);

            $('#editMemberForm').submit(function(e) {
                e.preventDefault(); // 阻止默认的表单提交行为

                var formData = $(this).serialize(); // 获取表单数据

                $.ajax({
                    url: '/members/' + $('#member_id').val(),
                    method: 'PUT',
                    data: formData,
                    success: function(response) {
                        alert('会员更新成功！');
                        loadMembers(); // 更新成功后重新加载会员列表
                    },
                    error: function(xhr, status, error) {
                        alert('更新会员失败，请稍后重试。');
                        console.error(xhr.responseText);
                    }
                });
            });
        },
        error: function() {
            alert('获取会员信息失败，请稍后再试。');
        }
    });
}

function deleteMember(memberId) {
    if (confirm('确定要删除这个会员吗？')) {
        $.ajax({
            url: `/members/${memberId}`,
            method: 'DELETE',
            success: function() {
                alert('会员删除成功！');
                loadMembers(); // 重新加载会员数据
            },
            error: function() {
                alert('删除失败，请稍后再试。');
            }
        });
    }
}
