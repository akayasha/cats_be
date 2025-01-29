# Feed Component

The `FeedComponent` is an Angular standalone component that displays a feed of cat images. It allows users to upload, populate, delete, and load more images through infinite scrolling. It also handles error scenarios gracefully, displaying appropriate messages to the user.

---

## Features

1. **Display Feed of Cat Images**
    - Shows a grid of images with lazy loading for better performance.

2. **Upload New Images**
    - Uploads an image file to the server via the `CatImageService`.

3. **Delete Images**
    - Deletes an image using its unique ID.

4. **Populate Feed**
    - Populates the feed with pre-existing or random images from the server.

5. **Infinite Scrolling**
    - Automatically loads more images as the user scrolls down.

6. **Error Handling**
    - Displays error messages on upload or any other failure scenarios.
    - Opens a dialog box with error details when a failure occurs.

---

## Dependencies

- [Angular Material](https://material.angular.io/) (for the dialog and styling)
- [CatImageService](../services/cat-image.service.ts) (service for API communication)
- [ErrorDialogComponent](../app/error-dialog/error-dialog.component.ts) (for displaying error dialogs)

---

## Installation

1. Clone the repository and navigate to your project directory.
2. Make sure Angular is installed in your environment.
3. Install the dependencies by running:

   ```
   npm install
   ```
4. Import the FeedComponent wherever needed:
    ```
     import { FeedComponent } from './components/feed.component';
    ```
---
## Usage
1. Add the component in your desired module or HTML file:

    ```
    <app-feed></app-feed>
    ```
2. Ensure the following services and dependencies are provided in your project:
    - `CatImageService` for API calls.
    - `MatDialog` for opening the error dialog.
    - Proper routing and backend endpoints for uploading, deleting, and retrieving images.

---

## Error Handling
The component handles errors gracefully:

- When an image upload fails, it extracts the error message from the server and displays it in both an error message div and a dialog box.

## Example of Error Display:
- <div class="error-message">{{ errorMessage }}</div>
     ```
     <div class="error-message">{{ errorMessage }}</div>
    ```
- Dialog-based error handling:
    ```
    this.dialog.open(ErrorDialogComponent, {
      width: '400px',
      data: { errorMessage: backendMessage }
    });
    ```
## Component Overview
### Properties

| Property         | Type         | Description                                              |
|------------------|--------------|----------------------------------------------------------|
| allImages        | CatImage[]   | All the cat images fetched from the server.             |
| displayedImages  | CatImage[]   | Images currently displayed in the feed.                 |
| itemsToShow      | number       | Number of images to display initially.                  |
| itemsPerScroll   | number       | Number of images to load on each scroll.                |
| isLoading        | boolean      | Indicates if the component is loading data.             |
| errorMessage     | string       | Error message to display on UI in case of a failure.    |

### Methods
| Method               | Description                                                                |
|----------------------|----------------------------------------------------------------------------|
| loadAllImages()      | Fetches all images from the server and displays the initial batch.         |
| onUpload(event)      | Handles image upload and displays errors in case of failure.               |
| populateImages()     | Populates the feed with server-side images.                                |
| openDeleteDialog(id) | Opens the confirmation dialog to delete an image.                          |
| closeDeleteDialog()  | Closes the delete confirmation dialog.                                     |
| onDelete()           | Deletes the selected image and reloads the feed.                          |
| onScroll()           | Loads more images when the user scrolls to the bottom of the page.         |
| onWindowScroll()     | Triggers onScroll() based on window scroll position.                       |


### Styling
Error messages are styled for visibility. Example CSS:
```
.error-message {
  color: red;
  font-weight: bold;
  margin: 10px 0;
}
```

### Future Improvements
1. add additional filtering or sorting options for images.
2. Improve user experience with visual loading indicators.
3. Enhance error handling for more comprehensive backend errors.

---

## Contributing
Feel free to submit issues or pull requests to enhance the functionality of this component.

---

## Licence
This project is licensed under the MIT License.




    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    