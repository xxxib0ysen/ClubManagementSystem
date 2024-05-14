function updateBreadcrumb(...crumbs) {
    let breadcrumbHtml = '<li class="breadcrumb-item"><a href="/static/index.html">首页</a></li>';
    crumbs.forEach((crumb, index) => {
        if (index === crumbs.length - 1) {
            breadcrumbHtml += `<li class="breadcrumb-item active" aria-current="page">${crumb}</li>`;
        } else {
            breadcrumbHtml += `<li class="breadcrumb-item"><a href="#">${crumb}</a></li>`;
        }
    });
    $('.breadcrumb').html(breadcrumbHtml);
}

function updateContent(targetId) {
    $('.nav-link').removeClass('active');
    $('#' + targetId).addClass('active');

    switch (targetId) {
        case 'homePage':
            loadHomePage();
            updateBreadcrumb('首页');
            break;
        case 'listClubs':
            loadClubs();
            updateBreadcrumb('社团管理', '社团列表');
            break;
        case 'addClub':
            loadAddClubForm();
            updateBreadcrumb('社团管理', '添加社团');
            break;
        case 'listMembers':
            loadMembers();
            updateBreadcrumb('会员管理', '会员列表');
            break;
        case 'addMember':
            loadAddMemberForm();
            updateBreadcrumb('会员管理', '添加会员');
            break;
        case 'menuManagement':
            loadMenuManagement();
            updateBreadcrumb('菜单管理');
            break;
        default:
            console.log('No such section');
    }
}
