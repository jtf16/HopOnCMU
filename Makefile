# Makefile 

# Locate directories
class_d=server/bin
source_d=server/src

JAVAC = javac
JFLAGS = -g
JAVA = java
CLEAN = rm -rf
sources = $(shell find $(source_d) -name "*.java")

all:
	# Compile server
	@$(JAVAC) $(JFLAGS) -d $(class_d) $(sources)

run:
	# Run server
	@$(JAVA) -classpath $(class_d) pt.ulisboa.tecnico.cmov.hoponcmu.communication.server.Server

clean:
	# Clean *.class files
	@$(CLEAN) $(class_d)/*
