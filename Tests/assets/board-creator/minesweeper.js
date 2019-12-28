const GAMEBOARD_ID = "gameBoard";
const CONTROLS_ID = "controls";

const BLANK_SQUARE_CLASS = "square blank";
const SQUARE_CLASS = "square";

// used for labeling different sections of the board
const UNCOLOURED_COLOUR = '#bcc0c4';
const NUMBERED_COLOUR = '#a8abad';
const COLOURS = [ UNCOLOURED_COLOUR, NUMBERED_COLOUR, '#1260de', '#e06767', '#734e4e', '#825d35', '#35cc58', '#7350de', 
                  '#eb26d0', '#e31010', '#ed8c42', '#f7d7be', '#83b5d6', '#f5930a', '#8f0414',
                  '#f2eb24'];
const NUM_COLUMNS_COLOURS = 6;

const FLAG_IMG_SRC = "flag.jpg";

const DEFAULT_NUM_ROWS = 5;
const DEFAULT_NUM_COLUMNS = 5;
const DEFAULT_NUM_MINES = 3;

let numberOfRows = DEFAULT_NUM_ROWS;
let numberOfColumns = DEFAULT_NUM_COLUMNS;
let numberOfMines = DEFAULT_NUM_MINES;
let fillSelected;
let lastSelectedElement;

window.onload = function() {
  // remove context menu, so we can right click
  document.oncontextmenu = function(e) {return false; };

  // add input event listeners for width / height
  document.getElementById('addWidth').addEventListener('click', function(){addWidth(1)});
  document.getElementById('addHeight').addEventListener('click', function(){addHeight(1)});
  document.getElementById('subtractWidth').addEventListener('click', function(){addWidth(-1)});
  document.getElementById('subtractHeight').addEventListener('click', function(){addHeight(-1)});
  
  initializeBoard();
  
  // create pallete of colours, mines flags etc
  createControls();
  
  //
  createKeyEventListener();
  
  document.getElementById('saveBoard').addEventListener('click', saveBoard);
  document.getElementById('loadBoard').addEventListener('click', loadBoard);
}

function initializeBoard(itemsToKeep) {
  // Create default board
  createBoardToUI(numberOfColumns, numberOfRows, itemsToKeep);
  document.getElementById('minesInput').value = numberOfMines;
}

function getElementByYX(y, x) {
  return document.getElementById(y + "_" + x);
}

function getElementFill(elementWithFill) {
  let backgroundColour = elementWithFill.style.backgroundColor;
  
  if (backgroundColour) {
    return rgb2hex(backgroundColour);
  }
  else {
    return elementWithFill.style.backgroundImage;
  }
}

function getType(elementFill) {
  let type = "";
  
  if (elementFill) {
    if (String(elementFill).includes("flag")) {
      type = "flag";
    }
    else if (String(elementFill).includes("mine")) {
      type = "mine";
    }
  }

  return type;
}

function saveBoard() {
  let squares = [];
  for (let x=0; x<numberOfColumns; x++) {
    for (let y=0; y<numberOfRows; y++) {
      let currentElement = getElementByYX(y, x);
      let elementFill = getElementFill(currentElement);

      if (elementFill) {
        if (elementFill == UNCOLOURED_COLOUR) {
          elementFill = "";
        }
      } else {
        elementFill = "";
      }
      
      let type = getType(elementFill);
      let colour = type == "" ? elementFill : "";
      
      squares.push({x: x, y: y, name: currentElement.innerHTML, colour: colour, type: type});
    }
  }
  
  let jsonData = {
    mines:  document.getElementById('minesInput').value,
    width: numberOfColumns,
    height: numberOfRows,
    squares: squares
  };
  console.log(JSON.stringify(jsonData));
}

function loadBoard() {
  let result = prompt('enter the JSON for the board');
  result = JSON.parse(result);
  
  if (result) {
    console.log(result);
    
    numberOfRows = result.height;
    numberOfColumns = result.width;
    numberOfMines = result.mines;
    
    let existingItems = [];
    
    // itemId, colour, type, innerHTML
    for (let i=0; i<result.squares.length; i++) {
      let square = result.squares[i];
      
      existingItems.push({itemId: square.y + "_" + square.x, type: square.type, innerHTML: square.name, colour: square.colour});
    }
    
    initializeBoard(existingItems);
  }
}

function createKeyEventListener() {
  document.addEventListener('keyup', (e) => {
    if (lastSelectedElement) {
      if (e.key == "Backspace" || e.key == "Delete") {
        lastSelectedElement.innerHTML = "|";
      } else if (e.key.length == 1) {
        lastSelectedElement.innerHTML = String(e.key).toUpperCase();
        
        // if it's a number
        if (!isNaN(e.key)) {
          lastSelectedElement.style.backgroundColor = NUMBERED_COLOUR;
        }
        
        lastSelectedElement = null;
      }
    }
  });
}

function createControls() {
  let htmlString = "<table id='colours'><thead></thead><tbody><tr>";
  
  // create buttons
  htmlString += createTDImage(FLAG_IMG_SRC);
  
  htmlString += "</tr><tr>";

  for (let i=0; i<COLOURS.length; i++) {
    htmlString += createTD(COLOURS[i]);
    if (i > 0 && i % (NUM_COLUMNS_COLOURS - 1) === 0) {
      htmlString += "</tr><tr>";
    }
  }
  
  htmlString += "</tr></tbody></table>";
  
  document.getElementById(CONTROLS_ID).innerHTML = htmlString;
}

function createTDImage(imageSrc) {
  return "<td style='background-image: url(\"" + imageSrc + "\")' onclick='setFill(\"" + imageSrc + "\")'></td>";
}

function createTD(colour) {
  return "<td style='background-color: " + colour + "' onclick='setFill(\"" + colour + "\")'></td>";
}

function setFill(colour) {
  fillSelected = colour;
}

function createBoardToUI(numberOfColumns, numberOfRows, existingItems) {
  document.getElementById(GAMEBOARD_ID).innerHTML = "";
  for (let i=0; i<numberOfRows; i++) {
    document.getElementById(GAMEBOARD_ID).innerHTML += createRow(i, numberOfColumns);
  }
  
  document.getElementById('widthInput').value = numberOfColumns;
  document.getElementById('heightInput').value = numberOfRows;
  
  if (existingItems) {
    // re-create old items
    for (let i=0; i<existingItems.length; i++) {
      let elementToModify = document.getElementById(existingItems[i].itemId);
      
      if (elementToModify) {
        elementToModify.style.backgroundColor = existingItems[i].colour;
        elementToModify.innerHTML = existingItems[i].innerHTML;
        elementToModify.style.backgroundImage = getBackgroundFromType(existingItems[i].type);
      }
    }
  }
}

function getBackgroundFromType(type) {
  if (type && type.includes("flag")) {
    return "url( " + FLAG_IMG_SRC + ")";
  }
  
  return "";
}

function getIdsWithChanges() {
  let results = [];
  let items = document.getElementsByClassName(SQUARE_CLASS);
  
  for (let i=0; i<items.length; i++) {
    if (items[i].style.backgroundImage) {
      results.push({itemId: items[i].id, colour: UNCOLOURED_COLOUR, background: items[i].style.backgroundImage, innerHTML: items[i].innerHTML});
    } else if (items[i].innerHTML) {
      results.push({itemId: items[i].id, colour: UNCOLOURED_COLOUR, innerHTML: items[i].innerHTML});
    } else if (items[i].style.backgroundColor) {
      let hexValue = rgb2hex(items[i].style.backgroundColor);
      if (hexValue != UNCOLOURED_COLOUR) {
        results.push({itemId: items[i].id, colour: hexValue, innerHTML: items[i].innerHTML});
      } else if (items[i].innerHTML) {
        results.push({itemId: items[i].id, colour: UNCOLOURED_COLOUR, innerHTML: items[i].innerHTML});
      }
    }
  }
  
  return results;
}

function rgb2hex(rgb) {
    rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
    function hex(x) {
        return ("0" + parseInt(x).toString(16)).slice(-2);
    }
    return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
}

function addWidth(amount) {
  numberOfColumns += amount;
  createBoardToUI(numberOfColumns, numberOfRows, getIdsWithChanges());
}

function addHeight(amount) {
  numberOfRows += amount;
  createBoardToUI(numberOfColumns, numberOfRows, getIdsWithChanges());
}

function addColour(elementClicked) {
  if (fillSelected) {
    if (fillSelected.startsWith("#")) {
      elementClicked.style.backgroundImage = "";
      elementClicked.style.backgroundColor = fillSelected;
    } else {
      elementClicked.style.backgroundImage = "url( " + fillSelected + ")";
      elementClicked.style.backgroundColor = "";
    }
  }
}

function createColumn(rowNumber, columnNumber) {
  return "<div class='" + BLANK_SQUARE_CLASS + "' id='" + (rowNumber + "_" + columnNumber) + "' onclick='addColour(this)' "
  + "oncontextmenu='typeIntoElement(this)'"
  + "></div>";
}

function typeIntoElement(elementToModify) {
  if (lastSelectedElement) {
    lastSelectedElement.innerHTML = "";
  }
  elementToModify.innerHTML += "|";
  lastSelectedElement = elementToModify;
}

function createRow(rowNumber, numberOfColumns) {
  let html = "<div id='row_" + rowNumber + "'>";
  
  for(let i=0; i<numberOfColumns; i++) {
    html += createColumn(rowNumber, i);
  }
  
  return html + "<div class='nextRow'></div>";
}
