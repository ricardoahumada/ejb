package com.banana.ejbs;

import javax.ejb.Local;

@Local
public interface LocalAccessToCaller {
	void triggerRemoteCall();
}
