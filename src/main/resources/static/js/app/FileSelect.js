// Define JavaScript functions for handling file uploads and input updates
function selectFile() {    // Handle file selection
  // Create an input element for file selection
  var input = document.createElement("input");
  input.type = "file";
  input.accept = ".html"; // Only allow HTML files to be uploaded

  // Add a change listener to the input element to handle file selection
  input.addEventListener("change", function(event) {

    // Get the selected file
    var file = event.target.files[0];

    // Create a FormData object to store the file data
    var formData = new FormData();
    formData.append("file", file);


    // Send an AJAX request to the server to upload the file and update the view
    var xhr = new XMLHttpRequest();

    // Specify the HTTP method (POST) and the URL (/upload) for the AJAX request
    xhr.open("POST", "/upload");

    // Add a callback function to be executed when the server responds to the request
    xhr.onload = function() {

      // Check the response status code to determine if the request was successful
      if (xhr.status === 200) {

        // Parse the response text from the server into a JSON object
        var response = JSON.parse(xhr.responseText);

        // Update the screenshot and table on the view
        document.getElementById("screenshot").src = response.screenshot;
        document.getElementById("table").innerHTML = response.tableHtml;
      } else {
        // Log an error message to the console if the request was not successful
        console.log("Error: " + xhr.statusText);
      }
    };
    // Send the FormData object containing the file data to the server
    xhr.send(formData);
  });

  // Trigger a click event on the input element to open the file selection dialog
  input.click();
}