![GitHub](https://img.shields.io/github/license/subratamazumder/java-file-processor?style=for-the-badge)
# java-file-processor
## Solution Design
This solution has been broken into 6 core extendable components.
- Main Processor

It is the entry point for the whole file processing solution which receives command line arguments & then instantiates high level objects like Reader, Extractor, Writer based on provided input & hand over controls when needed. It has no processing logic at all.

- Writer
	
Writes final output to STDOUT or a File based on configurable inputs

- File Reader 

Reads file in lazy manner into memory, once a line is read for a specific segment in current use case PID, then it hands over to Extractor to process further.

- Extractor 

Once a line is read then it splits all data items based on delimiter & extract positional data items in current scope (Name, DoB, Gender). Once extracted it hands over to Formatter & Validator to process it further 

- Formatter

Formats data based on configurable search indicator whether raw data needs formatting or not. It makes use of Validator to validate each data item.
  
- Validator

Validates data attribute as per Optionality defined in requirement sheet & along with basic length & character type checks. It also formats the date field & gender field to a more human readable format.

![programming-excercise (3)](https://user-images.githubusercontent.com/18535676/136715931-8a91a5b5-a033-46e5-9435-4f82930a1d89.png)
## Build Library Locally
```console
$ mvn clean install
```
## Testing via Java layer
```console
 Usage : $$java -jar java-file-processor-<version>.jar <path to file> <Segment Identified {allowed only PID for now}> <formatSearchResult {allowed only true or false (default=true)}> <writeToFile {allowed only true or false(default=false)}>
 e.g.; TO Process PID Segment with formatted search result : $ java -jar java-file-processor-1.0-SNAPSHOT.jar PID /mydir/files/file.txt true
```
## Testing via Shell Script layer
```console
 Usage : $ ./pid-file-processor.sh  <path to file> <formatSearchResult {allowed only true or false}> <writeToFile {allowed only true or false(default=false)}>
 e.g.; TO Process PID Segment with formatted search result & write to File : $ ./pid-file-processor.sh /mydir/files/file.txt true true
```
