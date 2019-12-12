package brickingbad.services;


public class AdapterHandler {

  private static Adapter currentAdapter;

  public static void setCurrentAdapter(Adapter adapter) {
    currentAdapter = adapter;
  }

  public static Adapter getCurrentAdapter() {
    return currentAdapter;
  }

}
