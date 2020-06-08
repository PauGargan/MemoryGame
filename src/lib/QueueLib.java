package lib;

import implementations.QueueD;

public class QueueLib {

	public static void moveTo(QueueD origin, QueueD target) {
		while (!origin.isEmpty()) {
			target.add(origin.first());
			origin.remove();
		}
	}
	
}
