/*
 * Copyright 2015, TopicQuests
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.topicquests.ks.ui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.topicquests.ks.SystemEnvironment;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>Title: StoryReader Engine</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008 Jack Park</p>
 *
 * <p>Company: NexistGroup</p>
 *
 * @author Jack Park
 */
public class StatsTab extends JPanel {
	private SystemEnvironment environment;
	private JButton refreshButton = new JButton("Refresh");
	private JTextArea statsArea = new JTextArea();
	
    public StatsTab() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void init(SystemEnvironment env) {
    	environment = env;
    	this.refreshButton_actionPerformed();
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        JPanel p1 = new JPanel(new FlowLayout());
        this.add(p1, BorderLayout.NORTH);
        p1.add(refreshButton);
        refreshButton.addActionListener(new
                StatsTabRefreshButton_actionAdapter(this));
        this.add(statsArea, BorderLayout.CENTER);
        statsArea.setText("");
        
    }

    public void refreshButton_actionPerformed() {
    	statsArea.setText(environment.getStats().getStats());
    }
    
    BorderLayout borderLayout1 = new BorderLayout();
}
class StatsTabRefreshButton_actionAdapter
		implements ActionListener {
	private StatsTab adaptee;
	StatsTabRefreshButton_actionAdapter(StatsTab adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.refreshButton_actionPerformed();
	}
}

