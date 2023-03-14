// Parse the uploaded HTML file using JSoup
Document doc = Jsoup.parse(file.getInputStream(), "UTF-8", "");

// Find all form elements in the HTML file
Elements formElements = doc.select("form");

// Attach a submit event listener to each form
formElements.forEach(formElement -> {
    // Get the ID of the form element
    String formId = formElement.attr("id");

    // Get the form element by its ID
    Element form = doc.selectFirst("#" + formId);

    // Attach a submit event listener to the form
    form.addEventListener("submit", event -> {
        // Prevent the default form submission behavior
        event.preventDefault();

        // Get the form data using FormData
        FormData formData = new FormData(form);

        // Make an AJAX request to the server to submit the form data
        XMLHttpRequest xhr = new XMLHttpRequest();
        xhr.open("POST", "/submit-form");
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function() {
            if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                // Handle the server response here
                console.log(this.responseText);
            }
        };
        xhr.send(formData);
    });
});
