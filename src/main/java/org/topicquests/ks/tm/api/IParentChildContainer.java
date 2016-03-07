/**
 * 
 */
package org.topicquests.ks.tm.api;

/**
 * @author park
 * <p>This API is for building conversation trees in which
 * it is necessary for each root node to contain a list of all
 * child nodes. This makes it easier to paint conversation trees.</p>
 * TODO: we are saving contextLocator, but that's not necessary since
 * each root is its own context
 */
public interface IParentChildContainer {
	/**
	 * DO NOT USE DIRECTLY, Only from {@link ISubjectProxyModel}
	 * @param contextLocator
	 * @param smallIcon
	 * @param locator
	 * @param subject
	 * @param transcluderLocator can be <code>null</code>
	 * @return <code>true</code> if child actually added
	 */
	boolean addChildNode(String contextLocator, String smallIcon, String locator, String subject, String transcluderLocator);
	
	/**
	 * DO NOT USE DIRECTLY, Only from {@link ISubjectProxyModel}
	 * @param contextLocator
	 * @param smallIcon
	 * @param locator
	 * @param subject
	 */
	void addParentNode(String contextLocator, String smallIcon, String locator, String subject);

	/**
	 * Builds a full list of child nodes for this root node
	 * @param locator
	 */
	void addToParentChildList(String locator);
}
