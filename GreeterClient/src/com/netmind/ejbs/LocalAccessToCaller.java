package com.netmind.ejbs;

import javax.ejb.Local;

@Local
public interface LocalAccessToCaller {
	void triggerRemoteCall();
}
