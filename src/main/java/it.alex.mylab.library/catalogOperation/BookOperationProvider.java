package it.alex.mylab.library.catalogOperation;

import it.alex.mylab.library.main.AvailableOperation;

@AvailableOperation
public interface BookOperationProvider {

     String getOperationName();

     void getOperation();

     String getOperationNumber();

     String getLevelAccess();
}
