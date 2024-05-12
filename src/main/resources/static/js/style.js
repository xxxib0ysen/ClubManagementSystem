// JavaScript for handling sidebar navigation and content loading
$(document).ready(function() {
    $('#loadClubManagement').click(function() {
        $('#mainContent').load('club_management.html');
    });
    $('#loadMemberManagement').click(function() {
        $('#mainContent').load('member_management.html');
    });
    $('#loadZTreeDisplay').click(function() {
        $('#mainContent').load('treeIndex.html');
    });
    $('#loadEChartsDisplay').click(function() {
        $('#mainContent').load('echarts.html');
    });
});
