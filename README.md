# java-file-processor
This solution has been broken into 4 core extendable components.
- File Reader 

Reads file in lazy manner into memory, once a line is read for a specific segment in current use case PID, then it hands over to Extractor to process further.

- Extractor 

Once a line is read then it splits all data items based on delimiter & extract positional data items in current scope (Name, DoB, Gender). Once extracted it hands over to Formatter & Validator to process it further 

- Formatter

Formats data based on configurable search indicator whether raw data needs formatting or not. It makes use of Validator to validate each data item.
  
- Validator

Validates data attribute as per Optionality defined in requirement sheet & along with basic length & character type checks. It also formats the date field & gender field to a more human readable format.


![programming-excercise (1)](https://user-images.githubusercontent.com/18535676/136686436-b9030566-084e-4b9f-a77a-a37e6103aaed.png)
