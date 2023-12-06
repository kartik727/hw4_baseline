package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The ExpenseTrackerModel class represents the model in the MVC
 * design pattern. It uses the Observer design pattern to notify
 * registered listeners of state changes.
 */
public class ExpenseTrackerModel {

  //encapsulation - data integrity
  private List<Transaction> transactions;
  private List<Integer> matchedFilterIndices;
  private List<ExpenseTrackerModelListener> listeners;

  // This is applying the Observer design pattern.                          
  // Specifically, this is the Observable class. 

  /**
   * Constructs an ExpenseTrackerModel object with no transactions,
   * no matched filter indices, and no registered listeners.
   */  
  public ExpenseTrackerModel() {
    transactions = new ArrayList<Transaction>();
    matchedFilterIndices = new ArrayList<Integer>();
    listeners = new ArrayList<ExpenseTrackerModelListener>();
  }

  /**
   * Adds a transaction to the model.
   *
   * @param t The transactions to be added to the model
   * @throws IllegalArgumentException If the transactions list is null
   */
  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
    stateChanged();
  }

  /**
   * Removes a transaction from the model.
   *
   * @param t The transactions to be removed from the model
   */
  public void removeTransaction(Transaction t) {
    transactions.remove(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
    stateChanged();
  }

  /**
   * Returns the unmodifiable list of transactions in the model.
   * 
   * @return The unmodifiable list of transactions
   */
  public List<Transaction> getTransactions() {
    //encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }

  /**
   * Sets the matched filter indices to the given list of indices.
   * Calling this method will notify all registered listeners of
   * a state change.
   *
   * @param newMatchedFilterIndices The new list of matched filter indices
   * @throws IllegalArgumentException If the new list of matched filter indices is null
   *                                 or if any of the indices are out of range
   */
  public void setMatchedFilterIndices(List<Integer> newMatchedFilterIndices) {
      // Perform input validation
      if (newMatchedFilterIndices == null) {
	      throw new IllegalArgumentException("The matched filter indices list must be non-null.");
      }
      for (Integer matchedFilterIndex : newMatchedFilterIndices) {
	      if ((matchedFilterIndex < 0) || (matchedFilterIndex > this.transactions.size() - 1)) {
	        throw new IllegalArgumentException("Each matched filter index must be between 0 (inclusive) and the number of transactions (exclusive).");
	      }
      }
      // For encapsulation, copy in the input list 
      this.matchedFilterIndices.clear();
      this.matchedFilterIndices.addAll(newMatchedFilterIndices);
      stateChanged();
  }

  /**
   * Returns the unmodifiable list of matched filter indices.
   * 
   * @return The unmodifiable list of matched filter indcices
   */
  public List<Integer> getMatchedFilterIndices() {
      // For encapsulation, copy out the output list
      List<Integer> copyOfMatchedFilterIndices = new ArrayList<Integer>();
      copyOfMatchedFilterIndices.addAll(this.matchedFilterIndices);
      return copyOfMatchedFilterIndices;
  }

  /**
   * Registers the given ExpenseTrackerModelListener for
   * state change events.
   *
   * @param listener The ExpenseTrackerModelListener to be registered
   * @return If the listener is non-null and not already registered,
   *         returns true. If not, returns false.
   */   
  public boolean register(ExpenseTrackerModelListener listener) {
      // For the Observable class, this is one of the methods.
      if (listener == null) {
        return false;
      }
      if (listeners.contains(listener)) {
        return false;
      }
      listeners.add(listener);
      return true;
  }

  /**
   * Unregisters the given ExpenseTrackerModelListener from
   * state change events.
   *
   * @param listener The ExpenseTrackerModelListener to be unregistered
   * @return If the listener is non-null and registered, returns true.
   *         If not, returns false.
   */
  public boolean unregister(ExpenseTrackerModelListener listener) {
      if (listener == null) {
        return false;
      }
      if (!listeners.contains(listener)) {
        return false;
      }
      listeners.remove(listener);
      return true;
    }

  /**
   * Returns the number of registered listeners.
   *
   * @return The number of registered listeners
   */
  public int numberOfListeners() {
      // For testing, this is one of the methods.
      return listeners.size();
  }

  /**
   * Returns true if the given ExpenseTrackerModelListener is
   * registered for state change events.
   *
   * @param listener The ExpenseTrackerModelListener to be checked
   * @return If the listener is non-null and registered, returns true.
   *         If not, returns false.
   */
  public boolean containsListener(ExpenseTrackerModelListener listener) {
      // For testing, this is one of the methods.
      return listeners.contains(listener);
  }

  /**
   * Notifies all registered listeners of a state change.
   * This method is called when transactions are added or removed.
   */
  protected void stateChanged() {
      // For the Observable class, this is one of the methods.
      for (ExpenseTrackerModelListener listener : listeners) {
        listener.update(this);
      }
  }
}
