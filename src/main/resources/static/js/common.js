
$(document).ready(function() {
    loadHomePage(); // 加载首页内容

    // 为导航链接添加点击事件处理程序
    $('.nav-link').on('click', function(e) {
        e.preventDefault(); // 阻止默认的链接跳转行为
        const targetId = $(this).attr('id'); // 获取被点击链接的ID
        updateContent(targetId); // 更新页面内容
    });
});

// 更新页面内容函数
function updateContent(targetId) {
    $('.nav-link').removeClass('active');
    $('#' + targetId).addClass('active');

    // 根据被点击链接的ID加载对应的内容
    switch (targetId) {
        case 'homePage':
            loadHomePage(); // 加载首页内容
            updateBreadcrumb('首页'); 
            break;
        case 'listClubs':
            loadClubs(); // 加载社团列表内容
            updateBreadcrumb('社团管理', '社团列表'); 
            break;
        case 'addClub':
            loadAddClubForm(); // 加载添加社团表单
            updateBreadcrumb('社团管理', '添加社团'); 
            break;
        case 'editClub':
            loadEditClubForm(); // 加载编辑社团表单
            updateBreadcrumb('社团管理', '编辑社团'); 
            break;
        case 'listMembers':
            loadMembers(); // 加载会员列表内容
            updateBreadcrumb('会员管理', '会员列表'); 
            break;
        case 'addMember':
            loadAddMemberForm(); // 加载添加会员表单
            updateBreadcrumb('会员管理', '添加会员'); 
            break;
        case 'editMember':
            loadEditMemberForm(); // 加载编辑会员表单
            updateBreadcrumb('会员管理', '编辑会员'); 
            break;

        default:
            console.log('No such section'); // 如果没有匹配的链接ID，输出错误信息
    }
}

函数
function updateBreadcrumb(...crumbs) {
    let breadcrumbHtml = '<li class="breadcrumb-item"><a href="/static/index.html">首页</a></li>'; // 面包屑导航的初始HTML
    crumbs.forEach((crumb, index) => {
        if (index === crumbs.length - 1) {
            // 如果是最后一个面包屑，设置为当前页面
            breadcrumbHtml += `<li class="breadcrumb-item active" aria-current="page">${crumb}</li>`;
        } else {
            // 否则，添加一个链接
            breadcrumbHtml += `<li class="breadcrumb-item"><a href="#">${crumb}</a></li>`;
        }
    });
    $('.breadcrumb').html(breadcrumbHtml); // 更新页面中的面包屑导航
}
