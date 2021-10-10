#echo "No of records to be processed"
#wc -l $1
java -jar target/java-file-processor-1.0-SNAPSHOT.jar $1 PID $2
