package rules.designPatterns;

public interface IObservable {

	public void addObserver(IObserver o);
    public void removeObserver(IObserver o);
    public Object get();
	
}
