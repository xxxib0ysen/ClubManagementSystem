$(document).ready(function() {
    loadHomePage();

    $('.nav-link').on('click', function (e) {
        e.preventDefault();
        const targetId = $(this).attr('id');
        updateContent(targetId);
    });

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
            case 'editClub':
                loadEditClubForm();
                updateBreadcrumb('社团管理', '编辑社团');
                break;
            case 'listMembers':
                loadMembers();
                updateBreadcrumb('会员管理', '会员列表');
                break;
            case 'addMember':
                loadAddMemberForm();
                updateBreadcrumb('会员管理', '添加会员');
                break;
            case 'editMember':
                loadEditMemberForm();
                updateBreadcrumb('会员管理', '编辑会员');
                break;
            default:
                console.log('No such section');
        }
    }
});