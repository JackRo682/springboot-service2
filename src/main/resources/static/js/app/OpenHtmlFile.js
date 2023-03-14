@GetMapping("/render-html")
public ResponseEntity<String> renderHtml(@RequestParam("fileName") String fileName) {
    // Load the HTML template with the specified filename into a string
    String html = loadHtmlTemplate(fileName);

    // Inject the JavaScript code into the HTML template
    String injectedHtml = injectJavaScriptCode(html);

    // Return the rendered HTML
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.TEXT_HTML);
    return new ResponseEntity<>(injectedHtml, headers, HttpStatus.OK);
}
