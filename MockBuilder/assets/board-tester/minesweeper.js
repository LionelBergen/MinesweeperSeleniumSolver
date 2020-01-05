const GAMEBOARD_ID = "gameBoard";
const CONTROLS_ID = "controls";

const BLANK_SQUARE_CLASS = "square blank";
const SQUARE_CLASS = "square";

// used for labeling different sections of the board
const UNCOLOURED_COLOUR = '#bcc0c4';
const NUMBERED_COLOUR = '#a8abad';

const FLAG_IMG_SRC = "flag.png";

const DEFAULT_NUM_ROWS = 5;
const DEFAULT_NUM_COLUMNS = 5;
const DEFAULT_NUM_MINES = 3;

let numberOfRows = DEFAULT_NUM_ROWS;
let numberOfColumns = DEFAULT_NUM_COLUMNS;
let numberOfMines = DEFAULT_NUM_MINES;
let fillSelected;
let lastSelectedElement;

// order matters
const NAMES_OF_NUMBER_OF_MINES = ["ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT"];

window.onload = function() {
  // remove context menu, so we can right click
  document.oncontextmenu = function(e) {return false; };
  
  initializeBoard();
  
  //
  createKeyEventListener();
  
  document.getElementById('loadBoard').addEventListener('click', loadBoard);
}

function initializeBoard(itemsToKeep) {
  // Create default board
  createBoardToUI(numberOfColumns, numberOfRows, itemsToKeep);
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

function loadBoard() {
  let result = prompt('enter the JSON for the board');
  result = JSON.parse(result);
  
  if (result) {
    numberOfRows = result.height;
    numberOfColumns = result.width;
    numberOfMines = result.mines;
    
    document.getElementById('minesLeft').value = numberOfMines;
    
    let existingItems = [];
    
    // itemId, colour, type, innerHTML
    for (let i=0; i<result.squares.length; i++) {
      let square = result.squares[i];
      
      existingItems.push({itemId: square.x + "_" + square.y, type: square.type, innerHTML: square.name, colour: square.colour});
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

function createTDImage(imageSrc) {
  return "<td style='background-image: url(\"" + imageSrc + "\")'></td>";
}

function createTD(colour) {
  return "<td style='background-color: " + colour + "'></td>";
}

function createBoardToUI(numberOfColumns, numberOfRows, existingItems) {
  document.getElementById(GAMEBOARD_ID).innerHTML = "";
  for (let i=0; i<numberOfRows; i++) {
    document.getElementById(GAMEBOARD_ID).innerHTML += createRow(i, numberOfColumns);
  }
  
  if (existingItems) {
    // re-create old items
    for (let i=0; i<existingItems.length; i++) {
      let elementToModify = document.getElementById(existingItems[i].itemId);
      
      if (elementToModify) {
        elementToModify.innerHTML = getInnerHTMLFromItem(existingItems[i]);
        elementToModify.style.backgroundImage = getBackgroundFromType(existingItems[i].type, existingItems[i].innerHTML);
      }
    }
  }
}

function getInnerHTMLFromItem(existingItem) {
  innerHTMLValue = existingItem.innerHTML;
  if (!innerHTMLValue) {
    if (existingItem.type && NAMES_OF_NUMBER_OF_MINES.includes(existingItem.type) && "ZERO" !== existingItem.type) {
      innerHTMLValue = NAMES_OF_NUMBER_OF_MINES.indexOf(existingItem.type);
    }
  }
  
  return innerHTMLValue;
}

function getBackgroundFromType(type, name) {
  if (type) {
    if (type.includes("flag") || type.includes("FLAGGED")) {
      return "url( " + FLAG_IMG_SRC + ")";
    } else if (NAMES_OF_NUMBER_OF_MINES.includes(type)) {
      return "url('square-active.png')";
    } else if (type.includes("ONE")) {
      return "url('square-active.png')";
    } else {
      console.log(type);
    }
  }
  else if (name && !isNaN(name)) {
    return "url('square-active.png')";
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
