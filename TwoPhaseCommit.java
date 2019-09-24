/**
 * The implementation of Two Phase Commit.
 * Every process sends a message to the coordinator.
 * The coordinator waits messages from majority of processes.
 * 
 *   @author naohaya
 */

public class TwoPhaseCommit extends Process {


	public TwoPhaseCommit(int id, MessageQueue mq) {
		/*
		 * call the constructor of superclass.
		 */
		super(id,mq);
		
		this.id = id;
	}

	public TwoPhaseCommit(int id, MessageQueue mq, boolean failure) {
		/*
		 * call the constructor of superclass.
		 */
		super(id,mq,failure);

		this.id = id;
	}

	public void run() {
		super.run();

		if (id > 0) {
			/* 
			* Coordinator
			*/

		} else {
			/*
			* Woker
			*/

		}
	}
}