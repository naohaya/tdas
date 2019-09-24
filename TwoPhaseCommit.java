/*
* TwoPhaseCommit
* The simple implementation of Two-Phase Commit Protocol.
* @naohaya
*/

public class TwoPhaseCommit extends Process {
	int id;
	MessageQueue mq;

	public TwoPhaseCommit (int id, MessageQueue mq) {
		super(id,mq);
		this.id = id;
		this.mq = mq;
			
		
	}

	public void run(){
		super.run();


		int count = 0;
		Object c = null;

		if(id > 0) {
			// worker

			while(true){
				if((c=receive()) != null ) {
					Integer i = (Integer)((Message)c).getContent();
					if (i.equals(1)){
						System.out.println("Received a prepare msg. from "+((Message) c).getSource() +" at id=" + this.id);
						send(0, new DefaultMessage(this.id, new Object()));
					}
					else if (i.equals(3)){
						System.out.println("Received a commit msg. from "+((Message) c).getSource() + " at id=" + this.id);
						break;
					}
					
				}
			}
		} else {
			// coordinator
			count = 0;
			while (count < mq.getTotalNum() -1) {
				send(count+1, new DefaultMessage(0, new Integer(1)));
				count ++;
			}

			count = 0;
			while (count < mq.getTotalNum() -1) {
				if((c=receive()) != null ) {
					System.out.println("Received an ack from "+((Message) c).getSource()+ " at id=" + this.id);
					count ++;
				}
			}

			// sending commit msgs.
			count = 0;
			while (count < mq.getTotalNum() -1) {
				send(count+1, new DefaultMessage(0, new Integer(3)));
				count ++;
			}

		}
	}
}