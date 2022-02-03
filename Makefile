all:
	@javac -d bin src/*.java

add: all
	@java -cp bin App add -d 2021-5-4 -r 4 -b 23:4 -w 8:55

add_no_date: all
	@java -cp bin App add -r 4 -b 23:4 -w 8:55


list: all
	@java -cp bin App list

stats: all
	@java -cp bin App stats