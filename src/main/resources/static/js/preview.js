function preview() {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPath = curWwwPath.substring(0, pos);
    window.open(localhostPath + "/pdfJS/web/viewer.html?file=/preview?fileName%3DCQUPT-WhitePaper-V1.2.pdf");
}


function previewPPT() {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPath = curWwwPath.substring(0, pos);
    window.open(localhostPath + "/pdfJS/web/viewer.html?file=/preview?fileName%3DCQUPT-V1.2.pdf");
}