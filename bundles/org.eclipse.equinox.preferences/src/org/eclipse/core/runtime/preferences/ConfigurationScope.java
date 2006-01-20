/*******************************************************************************
 * Copyright (c) 2004, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.core.runtime.preferences;

import java.net.URL;
import org.eclipse.core.internal.preferences.AbstractScope;
import org.eclipse.core.internal.preferences.PreferencesOSGiUtils;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.osgi.service.datalocation.Location;

/**
 * Object representing the configuration scope in the Eclipse preferences
 * hierarchy. Can be used as a context for searching for preference
 * values (in the IPreferencesService APIs) or for determining the 
 * correct preference node to set values in the store.
 * <p>
 * Configuration preferences are stored on a per configuration basis in the
 * platform's configuration area. (The configuration area typically
 * contains the list of plug-ins available for use, various settings
 * (those shared across different instances of the same configuration)
 * and any other such data needed by plug-ins.)
 * </p>
 * <p>
 * The path for preferences defined in the configuration scope hierarchy
 * is as follows: <code>/configuration/&lt;qualifier&gt;</code>
 * </p>
 * <p>
 * This class is not intended to be subclassed. This class may be instantiated.
 * </p>
 * @since 3.0
 */
public final class ConfigurationScope extends AbstractScope implements IScopeContext {

	/**
	 * String constant (value of <code>"configuration"</code>) used for the 
	 * scope name for the configuration preference scope.
	 */
	public static final String SCOPE = "configuration"; //$NON-NLS-1$

	/**
	 * Create and return a new configuration scope instance.
	 */
	public ConfigurationScope() {
		super();
	}

	/*
	 * @see org.eclipse.core.runtime.preferences.IScopeContext#getName()
	 */
	public String getName() {
		return SCOPE;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.IScopeContext#getNode(java.lang.String)
	 */
	public IEclipsePreferences getNode(String qualifier) {
		return super.getNode(qualifier);
	}

	/*
	 * @see org.eclipse.core.runtime.preferences.IScopeContext#getLocation()
	 */
	public IPath getLocation() {
		IPath result = null;
		Location location = PreferencesOSGiUtils.getDefault().getConfigurationLocation();
		if (!location.isReadOnly()) {
			URL url = location.getURL();
			if (url != null) {
				result = new Path(url.getFile());
				if (result.isEmpty())
					result = null;
			}
		}
		return result;
	}
}
