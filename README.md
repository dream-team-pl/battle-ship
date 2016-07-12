# battle-ship
* Code review steps: 
    - complete and clear javadoc
    - clean code 
    - pull the branch and check if the tests are passing(make all tests pass)
    - check if logger is used in a valid way( to not forget using logs )
* Branches: 
    - Feature/{name of the functionality} when you add a new functionality to the system
    - Fix-Request/{name of the functionality} when you want ask for a fix or some performance issues
    - Fixed/{name of the functionality} if you refactor or fix some issues
* Logger: 
    - Adding the logger to the class : final static Logger logger = Logger.getLogger(classname.class);
    - It is better to use logger.debug to debug and write the method and the class name inside the message
    - It is better to use the logger.error(Object message, Throwable t) if exception chached
    
