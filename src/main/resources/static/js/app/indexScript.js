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
    xhr.open("POST", "/upload");
    xhr.onload = function() {
      if (xhr.status === 200) {
        var response = JSON.parse(xhr.responseText);

        // Update the screenshot and table on the view
        document.getElementById("screenshot").src = response.screenshot;
        document.getElementById("table").innerHTML = response.tableHtml;
      } else {
        console.log("Error: " + xhr.statusText);
      }
    };
    xhr.send(formData);
  });

  // Trigger a click event on the input element to open the file selection dialog
  input.click();
}

function handleInputChange(tableName, inputName, inputValue) {
  // Create a new AJAX request to update the database with the new input value
  var xhr = new XMLHttpRequest();

  // Specify the URL and HTTP method for the request
  xhr.open("POST", "/update");

  // Set the content type of the request
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

  // Define what to do when the response comes back
  xhr.onload = function() {
    // If the response is successful, log a message to the console
    if (xhr.status === 200) {
      console.log("Input updated successfully!");
    } else {
      // If there's an error, log the error message to the console
      console.log("Error: " + xhr.statusText);
    }
  };

  // Send the request with the table name, input name, and input value as parameters
  xhr.send("tableName=" + tableName + "&inputName=" + inputName + "&inputValue=" + inputValue);
}
