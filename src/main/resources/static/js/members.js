function loadMembers(pageNumber = 1) {
    var htmlContent = `
    <div class="row mb-3">
        <div class="col">
            <input type="text" class="form-control" id="searchMemberInput" placeholder="搜索会员">
        </div>
        <div class="col-auto">
            <button class="btn btn-primary" onclick="searchMembers()">搜索</button>
            <button class="btn btn-secondary" onclick="resetSearch()">重置</button>
        </div>
    </div>
    <div class="mb-3">
        <button class="btn btn-success" id="addMemberButton">新增会员</button>
        <button class="btn btn-danger" onclick="batchDeleteMembers()">批量删除</button>
    </div>
    <table class="table">
        <thead>
            <tr>
                <th scope="col"><input type="checkbox" id="selectAllMembers" onclick="toggleSelectAll(this, 'memberCheckbox')"></th>
                <th scope="col">成员ID</th>
                <th scope="col">成员名</th>
                <th scope="col">所属社团</th>
                <th scope="col">操作</th>
            </tr>
        </thead>
        <tbody id="memberListTable">
            <!-- 会员数据将通过JavaScript动态加载 -->
        </tbody>
    </table>
    <nav>
        <ul class="pagination" id="pagination">
            <!-- 分页按钮将通过JavaScript动态加载 -->
        </ul>
    </nav>`;
    $('#content').html(htmlContent);
    loadMemberData(pageNumber); // 调用函数加载并展示会员数据

    // 绑定新增会员按钮的点击事件
    $('#addMemberButton').on('click', function() {
        loadAddMemberForm();
    });
}

function loadMemberData(pageNumber = 1) {
    const searchTerm = $('#searchMemberInput').val().trim();

    $.ajax({
        url: '/members/page',
        method: 'GET',
        data: {
            page: pageNumber,
            size: 5,
            searchTerm: searchTerm
        },
        success: function(response) {
            console.log(response); // 打印从后端返回的完整响应数据
            const tbody = $('#memberListTable');
            tbody.empty();

            response.list.forEach(member => {
                tbody.append(`
                <tr>
                    <td><input type="checkbox" class="memberCheckbox" value="${member.member_id}"></td>
                    <td>${member.member_id}</td>
                    <td>${member.member_name}</td>
                    <td>${member.club_name}</td>
                    <td>
                        <button class="btn btn-info" onclick="editMember(${member.member_id})">编辑</button>
                        <button class="btn btn-danger" onclick="deleteMember(${member.member_id})">删除</button>
                    </td>
                </tr>
                `);
            });

            // 分页处理
            const pagination = $('#pagination');
            pagination.empty();
            for (let i = 1; i <= response.pages; i++) {
                pagination.append(`<li class="page-item ${i === pageNumber ? 'active' : ''}">
                    <a class="page-link" href="#" onclick="loadMembers(${i})">${i}</a>
                </li>`);
            }
        },
        error: function() {
            $('#memberListTable').html('<tr><td colspan="5">加载数据失败，请稍后重试。</td></tr>');
        }
    });
}

function searchMembers() {
    loadMembers(1); // 重新加载数据，从第一页开始
}

function resetSearch() {
    $('#searchMemberInput').val(''); // 清空搜索输入框
    loadMembers(); // 重新加载数据
}

function toggleSelectAll(selectAllCheckbox, checkboxClass) {
    const checkboxes = $(`.${checkboxClass}`);
    checkboxes.prop('checked', selectAllCheckbox.checked);
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

        var formData = {
            member_name: $('#memberName').val(),
            club_id: $('#clubId').val()
        };

        $.ajax({
            url: '/members/',
            method: 'POST',
            contentType: 'application/json', // 设置内容类型
            data: JSON.stringify(formData), // 序列化数据
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
function editMember(member_id) {
    // 发送Ajax请求获取特定ID的会员信息
    $.ajax({
        url: '/members/' + member_id,
        method: 'GET',
        success: function(member) {
            // 将会员信息填充到表单中
            $('#content').html(`
            <h2>编辑会员</h2>
            <form id="editMemberForm">
                <input type="hidden" id="member_id" name="member_id" value="${member.member_id}">
                <div class="form-group">
                    <label for="member_name">会员名称</label>
                    <input type="text" class="form-control" id="member_name" name="member_name" value="${member.member_name}" required>
                </div>
                <div class="form-group">
                    <label for="club_id">所属社团ID</label>
                    <input type="text" class="form-control" id="club_id" name="club_id" value="${member.club_id}" required>
                </div>
                <button type="submit" class="btn btn-primary">保存</button>
            </form>
            `);

            $('#editMemberForm').submit(function(e) {
                e.preventDefault(); // 阻止默认的表单提交行为

                var formData = {
                    member_id: $('#member_id').val(),
                    member_name: $('#member_name').val(),
                    club_id: $('#club_id').val()
                };

                // 发送Ajax请求更新会员信息
                $.ajax({
                    url: '/members/' + $('#member_id').val(),
                    method: 'PUT',
                    contentType: 'application/json', // 设置内容类型
                    data: JSON.stringify(formData), // 序列化数据
                    success: function(response) {
                        alert('会员信息更新成功！');
                        loadMembers(); // 更新成功后重新加载会员列表
                    },
                    error: function(xhr, status, error) {
                        alert('会员信息更新失败，请稍后再试。');
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

function deleteMember(member_id) {
    if (confirm('确定要删除这个会员吗？')) {
        $.ajax({
            url: `/members/${member_id}`,
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

function batchDeleteMembers() {
    var selectedmember_ids = $('.memberCheckbox:checked').map(function() { return $(this).val(); }).get();
    if (selectedmember_ids.length === 0) {
        alert('请选择要删除的会员。');
        return;
    }
    if (confirm('确定要删除选中的会员吗？')) {
        $.ajax({
            url: '/members/batch-delete',
            method: 'DELETE',
            data: JSON.stringify(selectedmember_ids),
            contentType: 'application/json',
            success: function() {
                alert('批量删除会员成功！');
                loadMembers(); // 重新加载会员数据
            },
            error: function() {
                alert('批量删除失败，请稍后再试。');
            }
        });
    }
}
