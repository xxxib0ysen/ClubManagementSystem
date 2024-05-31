// 加载社团列表页面，包含搜索栏、新增按钮和批量删除按钮
function loadClubs(pageNumber = 1, searchTerm = '') {
    // 定义页面内容的HTML结构
    const htmlContent = `
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
                <th scope="col"><input type="checkbox" id="selectAllClubs" onclick="toggleSelectAllClubs(this)"></th>
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

    // 将HTML内容插入到页面中
    $('#content').html(htmlContent);

    // 加载社团数据
    loadClubData(pageNumber, searchTerm);

    // 绑定新增按钮的点击事件
    $('#addClubButton').on('click', loadAddClubForm);
    // 设置搜索框的初始值
    $('#searchClubInput').val(searchTerm);
}

// 加载社团数据并动态生成表格内容
function loadClubData(pageNumber = 1, searchTerm = '') {
    $.ajax({
        url: '/clubs/page',
        method: 'GET',
        data: { page: pageNumber, size: 5, club_name: searchTerm },
        success: function(response) {
            const tbody = $('#clubListTable');
            tbody.empty(); // 清空表格内容
            // 遍历返回的社团数据，生成表格行
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
            $('#selectAllClubs').prop('checked', false); // 取消全选
            // 渲染分页按钮
            renderPagination(response.pages, pageNumber, searchTerm);
        },
        error: function() {
            $('#clubListTable').html('<tr><td colspan="6">加载数据失败，请稍后重试。</td></tr>');
        }
    });
}

// 渲染分页按钮
function renderPagination(totalPages, currentPage, searchTerm) {
    const pagination = $('#pagination');
    pagination.empty(); // 清空分页内容
    for (let i = 1; i <= totalPages; i++) {
        pagination.append(`<li class="page-item ${i === currentPage ? 'active' : ''}">
            <a class="page-link" href="#" onclick="loadClubs(${i}, '${searchTerm}')">${i}</a>
        </li>`);
    }
}

// 搜索社团
function searchClubs() {
    // 获取搜索输入框的值并加载社团列表
    loadClubs(1, $('#searchClubInput').val().trim());
}

// 重置搜索
function resetClubSearch() {
    $('#searchClubInput').val(''); // 清空搜索输入框
    loadClubs(); // 加载所有社团
}

// 全选/取消全选功能
function toggleSelectAllClubs(selectAllCheckbox) {
    $('.clubCheckbox').prop('checked', selectAllCheckbox.checked); // 设置所有复选框的选中状态
}

// 加载添加或编辑社团表单模板
function loadFormTemplate(type, club = {}) {
    const formTitle = type === 'add' ? '添加社团' : '编辑社团';
    const clubIdInput = type === 'edit' ? `<input type="hidden" id="club_id" name="club_id" value="${club.club_id}">` : '';
    const existingImageUrlInput = type === 'edit' && club.image_url ? `<input type="hidden" id="existing_image_url" name="existing_image_url" value="${club.image_url}">` : '';
    const imageUrl = club.image_url ? `<img src="${club.image_url}" alt="${club.club_name}" style="width: 100px; margin-top: 10px;" id="clubImagePreview">` : '';

    // 设置表单HTML内容
    $('#content').html(`
    <h2>${formTitle}</h2>
    <form id="${type}ClubForm" enctype="multipart/form-data">
        ${clubIdInput}
        ${existingImageUrlInput}
        <div class="form-group">
            <label for="club_name">社团名称</label>
            <input type="text" class="form-control" id="club_name" name="club_name" value="${club.club_name || ''}" required>
        </div>
        <div class="form-group">
            <label for="description">描述</label>
            <textarea class="form-control" id="description" name="description" rows="3">${club.description || ''}</textarea>
        </div>
        <div class="form-group">
            <label for="club_image">社团图片</label>
            <input type="file" class="form-control-file" id="club_image" name="club_image" onchange="previewClubImage(event, '${type}')">
            ${imageUrl}
        </div>
        <button type="submit" class="btn btn-primary">提交</button>
    </form>
    `);

    // 提交表单时的处理逻辑
    $(`#${type}ClubForm`).submit(function(e) {
        e.preventDefault();
        const formData = new FormData(this);
        if (type === 'edit') formData.append('club_id', $('#club_id').val());

        const url = type === 'add' ? '/clubs/' : `/clubs/${$('#club_id').val()}`;
        const method = type === 'add' ? 'POST' : 'PUT';

        // 如果没有上传新图片，使用现有图片URL
        if ($('#club_image').get(0).files.length === 0) {
            formData.append('image_url', $('#existing_image_url').val());
        }

        // 发送Ajax请求提交表单
        $.ajax({
            url,
            method,
            data: formData,
            processData: false,
            contentType: false,
            success: function() {
                alert('操作成功！');
                loadClubs(); // 操作成功后重新加载社团列表
            },
            error: function(xhr) {
                alert('操作失败，请稍后再试。');
                console.error(xhr.responseText);
            }
        });
    });
}

// 加载新增社团表单
function loadAddClubForm() {
    loadFormTemplate('add');
}

// 加载编辑社团表单
function loadEditClubForm(club_id) {
    $.ajax({
        url: '/clubs/' + club_id,
        method: 'GET',
        success: function(club) {
            loadFormTemplate('edit', club);
        },
        error: function() {
            alert('获取社团信息失败，请稍后再试。');
        }
    });
}

// 预览上传的社团图片
function previewClubImage(event, type) {
    const output = document.getElementById(type === 'add' ? 'addClubImagePreview' : 'clubImagePreview');
    output.src = URL.createObjectURL(event.target.files[0]);
}

// 删除社团
function deleteClub(club_id) {
    if (confirm('确定要删除这个社团吗？')) {
        $.ajax({
            url: '/clubs/' + club_id,
            method: 'DELETE',
            success: function() {
                alert('社团删除成功！');
                loadClubs(); // 删除成功后重新加载社团列表
            },
            error: function() {
                alert('删除失败，请稍后再试。');
            }
        });
    }
}

// 批量删除社团
function batchDeleteClubs() {
    const selectedclub_ids = $('.clubCheckbox:checked').map(function() { return $(this).val(); }).get();
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
                loadClubs(); // 批量删除成功后重新加载社团列表
            },
            error: function() {
                alert('批量删除失败，请稍后再试。');
            }
        });
    }
}
