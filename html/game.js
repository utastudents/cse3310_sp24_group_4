
const highlightColors = [
                        'highlight-color-player1',
                        'highlight-color-player2',
                        'highlight-color-player3',
                        'highlight-color-player4'
                    ];
    // highlight color depends on player
    let currentPlayerIndex = 1;

const startGameBtn = document.querySelector('.btnStart');
const join = document.querySelector('.btn');
const leave = document.querySelector('.btn1');
const lobby = document.querySelector('.lobby');
const header = document.querySelector('header');
const body = document.querySelector('body');
const table = document.querySelector('table');
const header2 = document.getElementById('game-title');
    
startGameBtn.addEventListener('click', () => {
    lobby.classList.add('hidden');
    header.classList.add('hidden');
    //table.classList.remove('hidden');
    body.classList.add('remove-background');
    header2.classList.remove('hidden');
    //header2.classList.add('appear');
});
join.addEventListener('click', () => {
    
 });
leave.addEventListener('click', () => {
        
});


// Fetch word grid data from server
fetch('/wordgrid')
    .then(response => response.text())
    .then(htmlGrid => {
        // Convert the HTML string to DOM elements
        const tempDiv = document.createElement('div');
        tempDiv.innerHTML = htmlGrid.trim();

        // Create a new table element
        const newTable = document.createElement('table');
        newTable.style.borderCollapse = 'collapse'; // Ensure borders collapse properly

        // Create 10 rows and 10 columns in the table
        for (let i = 0; i < 10; i++) {
            const newRow = document.createElement('tr');
            for (let j = 0; j < 10; j++) {
                const newCell = document.createElement('td');
                newCell.style.border = '1px solid black'; // Add border around each cell
                newCell.style.textAlign = 'center'; // Center text horizontally
                newCell.style.verticalAlign = 'middle'; // Center text vertically
                newCell.style.width = '20px'; // Set width to 20px
                newCell.style.height = '15px'; // Set height to 15px
                newCell.style.cursor = 'pointer'; // Add cursor pointer
                // Copy the text content of the corresponding cell in the grid
                const gridCell = tempDiv.querySelector(`tr:nth-child(${i + 1}) td:nth-child(${j + 1})`);
                if (gridCell) {
                    newCell.textContent = gridCell.textContent;
                }
                newRow.appendChild(newCell); // Append the new cell to the new row
            }
            newTable.appendChild(newRow); // Append the new row to the new table
        }

        // Insert the new table into the wordGrid element
        document.getElementById('wordGrid').appendChild(newTable);

        // Add click event listener to table cells for highlighting
        newTable.querySelectorAll('td').forEach(cell => {
            cell.onclick = function() {
                const currentPlayerClass = `highlight-color-player${currentPlayerIndex + 1}`; // Determine current player's highlight class
                if (!cell.classList.contains(currentPlayerClass)) {
                    cell.classList.add(currentPlayerClass); // Add highlight class for current player
                } else {
                    cell.classList.remove(currentPlayerClass); // Remove highlight class for current player
                }
            };
        });
    })
    .catch(error => console.error('Error fetching word grid:', error));


        
// highlights letters
for (let node of document.querySelectorAll("td")){
    node.onclick = function() {
        if(node.className == ""){
            node.classList.add(highlightColors[currentPlayerIndex]);
        } else {
            node.className = ""
        }
    }
}
var connection = null;

var serverUrl;
serverUrl = "ws://" + window.location.hostname + ":9180";
// Create the connection with the server
connection = new WebSocket(serverUrl);

connection.onopen = function (evt) {
    console.log("open");
}
//if close then set topMessage to = Server Offline
connection.onclose = function (evt) {
    console.log("close");
    document.getElementById("topMessage").innerHTML = "Server Offline"
}

document.addEventListener('DOMContentLoaded', function() {
    const chatBox = document.getElementById('chatBox');
    const userInput = document.getElementById('userInput');
    const send = document.getElementById('send');

    send.addEventListener('click', function() {
        const message = userInput.value.trim();
        addMessage(message);
        userInput.value = '';
    });

    function addMessage(message) {
        connection.send("msg: "+ message);
        /*
        const msgElement = document.createElement('div');
        msgElement.textContent = message;       //text content of the new div
        chatBox.appendChild(msgElement);        //appending the new message to message history
        chatBox.scrollTop = chatBox.scrollHeight;
        */

        //call function in messaging for other player to see message
    }
});

connection.onmessage = function (evt) {
    var msg;
    msg = evt.data;
    //console.log("Message received: " + msg);
    if (msg.startsWith('"msg:')) {
        // If yes, remove the prefix
        msg = msg.substring(6, msg.length -1);
        const msgElement = document.createElement('div');
        msgElement.textContent = msg;       //text content of the new div
        chatBox.appendChild(msgElement);        //appending the new message to message history
        chatBox.scrollTop = chatBox.scrollHeight;
    }

    /*
    
    const msgElement = document.createElement('div');
    msgElement.textContent = msg;       //text content of the new div
    chatBox.appendChild(msgElement);        //appending the new message to message history
    chatBox.scrollTop = chatBox.scrollHeight;
    
    //converting msg to obj
    const obj = JSON.parse(msg);*/

    /*
    //if setting up players
    if ('YouAre' in obj) {
        if (obj.YouAre == "XPLAYER") {
            idx = 0;
        }
        else {
            idx = 1;
        }

        gameid = obj.GameId;
    }
    //check if our turn
    else if ('CurrentTurn' in obj) {
        // only pay attention to this game
        if (gameid == obj.GameId) {
            // button state to display values

            document.getElementById("b1").value = ButtonStateToDisplay.get(obj.Button[0]);
            document.getElementById("b2").value = ButtonStateToDisplay.get(obj.Button[1]);
            document.getElementById("b3").value = ButtonStateToDisplay.get(obj.Button[2]);
            document.getElementById("b4").value = ButtonStateToDisplay.get(obj.Button[3]);
            document.getElementById("b5").value = ButtonStateToDisplay.get(obj.Button[4]);
            document.getElementById("b6").value = ButtonStateToDisplay.get(obj.Button[5]);
            document.getElementById("b7").value = ButtonStateToDisplay.get(obj.Button[6]);
            document.getElementById("b8").value = ButtonStateToDisplay.get(obj.Button[7]);
            document.getElementById("b9").value = ButtonStateToDisplay.get(obj.Button[8]);


            // displaying MSG FROM BACKEND HERE
            document.getElementById("topMessage").innerHTML = obj.Msg[idx];
            document.getElementById("bottomMessage").innerHTML = "Number of games: " + obj.NumGames + "\nNumber of games in progress: " + obj.CurrentGames + "\nX wins: " + obj.XWon + "\nY wins: " + obj.YWon + "\nDraws: " + obj.Draw;
        }
    }
    else if('CurrentGames' in obj) {
        document.getElementById("bottomMessage").innerHTML = "Number of games: " + obj.NumGames + "\nNumber of games in progress: " + obj.CurrentGames + "\nX wins: " + obj.XWon + "\nY wins: " + obj.YWon + "\nDraws: " + obj.Draw;
    }*/

    
}

function toggleInstructions() {
    var instructions = document.getElementById("instructions");
    if (instructions.style.display === "none") {
        instructions.style.display = "block";
      } else {
        instructions.style.display = "none";
      }
}

    // gets the wordbank sent from server
    fetch('/wordbank')
    .then(response => response.text())
    .then(htmlWordBank => {
        const wordBankDiv = document.getElementById('wordBank');
        wordBankDiv.innerHTML = htmlWordBank;
    })
    .catch(error => console.error('Error fetching word bank:', error));

    let firstCell = null;
let secondCell = null;

wordGrid.forEach((row, rowIndex) => {
    row.forEach((cell, colIndex) => {
        cell.onclick = function() {
            if (!firstCell) {
                // First cell clicked
                firstCell = [rowIndex, colIndex];
            } else if (!secondCell) {
                // Second cell clicked
                secondCell = [rowIndex, colIndex];
                // Process the selected cells
                processSelectedCells();
            } else {
                // Reset if more than two cells clicked
                firstCell = null;
                secondCell = null;
            }
        };
    });
});

function processSelectedCells() {
    // Ensure both cells are selected
    if (!firstCell || !secondCell) return;

    // Ensure cells are not the same
    if (firstCell[0] === secondCell[0] && firstCell[1] === secondCell[1]) return;

    // Determine the direction of selection (horizontal, vertical, or diagonal)
    const direction = determineSelectionDirection(firstCell, secondCell);

    // Extract letters between the two selected cells
    const letters = extractLettersBetweenCells(firstCell, secondCell, direction);

    // Concatenate letters to form a word
    const selectedWord = letters.join('').toUpperCase();

    // Check if the selected word exists in the word bank
    if (wordBank.includes(selectedWord)) {
        // Highlight all cells between the two clicked cells
        highlightCellsBetween(firstCell, secondCell, direction);
    }

    // Reset selected cells
    firstCell = null;
    secondCell = null;
}

function determineSelectionDirection(firstCell, secondCell) {
    const [row1, col1] = firstCell;
    const [row2, col2] = secondCell;

    if (row1 === row2) {
        // Horizontal selection
        return 'horizontal';
    } else if (col1 === col2) {
        // Vertical selection
        return 'vertical';
    } else if (Math.abs(row2 - row1) === Math.abs(col2 - col1)) {
        // Diagonal selection
        return 'diagonal';
    } else {
        // Invalid selection
        return 'invalid';
    }
}

function extractLettersBetweenCells(firstCell, secondCell, direction) {
    const [row1, col1] = firstCell;
    const [row2, col2] = secondCell;
    const letters = [];

    if (direction === 'horizontal') {
        // Extract letters horizontally
        const startCol = Math.min(col1, col2);
        const endCol = Math.max(col1, col2);
        for (let col = startCol + 1; col < endCol; col++) {
            letters.push(wordGrid[row1][col].textContent);
        }
    } else if (direction === 'vertical') {
        // Extract letters vertically
        const startRow = Math.min(row1, row2);
        const endRow = Math.max(row1, row2);
        for (let row = startRow + 1; row < endRow; row++) {
            letters.push(wordGrid[row][col1].textContent);
        }
    } else if (direction === 'diagonal') {
        // Extract letters diagonally
        const deltaRow = row2 > row1 ? 1 : -1;
        const deltaCol = col2 > col1 ? 1 : -1;
        let currentRow = row1 + deltaRow;
        let currentCol = col1 + deltaCol;
        while (currentRow !== row2 && currentCol !== col2) {
            letters.push(wordGrid[currentRow][currentCol].textContent);
            currentRow += deltaRow;
            currentCol += deltaCol;
        }
    }

    return letters;
}

function highlightCellsBetween(firstCell, secondCell, direction) {
    const [row1, col1] = firstCell;
    const [row2, col2] = secondCell;

    if (direction === 'horizontal') {
        const startCol = Math.min(col1, col2);
        const endCol = Math.max(col1, col2);
        for (let col = startCol + 1; col < endCol; col++) {
            wordGrid[row1][col].classList.add('highlighted');
        }
    } else if (direction === 'vertical') {
        const startRow = Math.min(row1, row2);
        const endRow = Math.max(row1, row2);
        for (let row = startRow + 1; row < endRow; row++) {
            wordGrid[row][col1].classList.add('highlighted');
        }
    } else if (direction === 'diagonal') {
        const deltaRow = row2 > row1 ? 1 : -1;
        const deltaCol = col2 > col1 ? 1 : -1;
        let currentRow = row1 + deltaRow;
        let currentCol = col1 + deltaCol;
        while (currentRow !== row2 && currentCol !== col2) {
            wordGrid[currentRow][currentCol].classList.add('highlighted');
            currentRow += deltaRow;
            currentCol += deltaCol;
        }
    }
}


/*
var idx = -1;
var gameid = -1;
class UserEvent {
    Button = -1;
    PlayerIdx = 0;
    GameId = 0;
}
var connection = null;

var serverUrl;
serverUrl = "ws://" + window.location.hostname + ":9880";
// Create the connection with the server
connection = new WebSocket(serverUrl);

connection.onopen = function (evt) {
    console.log("open");
}
connection.onclose = function (evt) {
    console.log("close");
    document.getElementById("topMessage").innerHTML = "Server Offline"
}    

}*/
