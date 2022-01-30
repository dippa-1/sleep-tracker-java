all:
	@javac -d bin src/*.java

run: all
	@java -cp bin App -d 2021-5-4 -r 4 -b 23:4 -w 0:55
