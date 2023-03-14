function openHtmlFile(event) {
    event.preventDefault();  // prevent the default behavior of clicking on a link

    var fileName = event.currentTarget.href;  // get the href attribute of the link
    window.open(fileName);  // open the HTML file in a new browser window or tab
}