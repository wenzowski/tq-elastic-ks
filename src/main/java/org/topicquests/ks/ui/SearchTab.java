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

import javax.swing.*;

import org.topicquests.ks.SearchEnvironment;
import org.topicquests.ks.api.IExtendedEnvironment;
import org.topicquests.ks.api.ITQCoreOntology;
import org.topicquests.ks.api.ITicket;
import org.topicquests.ks.TicketPojo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class SearchTab
    extends JPanel {
	private SearchEnvironment host;
	private ITicket credentials;
	private int start = 0; //TODO we need to be able to page
	private int count = 50;
	
  public SearchTab() {
    try {
      jbInit();
      credentials = new TicketPojo(ITQCoreOntology.SYSTEM_USER);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public void setEnvironment(IExtendedEnvironment e) {
	  host = e.getSearchEnvironment();
	  if (host == null)
		  throw new RuntimeException("SearchTab null SearchEnvironment");
  }
  
	public void addSearchHit(String hit) {
		this.hitArea.append(hit+"\n");
	}
	
	public void setSearchHits(String allHist) {
		this.hitArea.setText(allHist);
	}


  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    searchField.setPreferredSize(new Dimension(200, 20));
    searchField.setText("");
    jPanel1.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    detailsButton.setToolTipText("Search on labels");
    detailsButton.setText("Details");
    detailsButton.addActionListener(new SearchTab_labelButton_actionAdapter(this));
    labelButton.setToolTipText("Search on keywords");
    labelButton.setText("Label");
    labelButton.addActionListener(new SearchTab_keywordButton_actionAdapter(this));
    hitArea.setEnabled(true);
    hitArea.setText("");
    hitArea.setLineWrap(true);
    jPanel1.add(searchField);
    jPanel1.add(detailsButton);
    jPanel1.add(labelButton);
    jScrollPane1.getViewport().add(hitArea);
    this.add(jScrollPane1, java.awt.BorderLayout.CENTER);
    this.add(jPanel1, java.awt.BorderLayout.NORTH);
  }

  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextField searchField = new JTextField();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton detailsButton = new JButton();
  JButton labelButton = new JButton();
  JTextArea hitArea = new JTextArea();
  
  public void detailsButton_actionPerformed(ActionEvent e) {
	  String language = "en"; //TODO fix this
	  String q = searchField.getText();
	  hitArea.setText("");
	  if (!q.equals(""))
		  host.acceptDetailsQuery(q,language,start,count,credentials);
  }

  public void labelButton_actionPerformed(ActionEvent e) {
	  String language = "en"; //TODO fix this
	  String q = searchField.getText();
	  hitArea.setText("");
	  if (!q.equals(""))
		  host.acceptLabelQuery(q,language,start,count,credentials);
  }
}

class SearchTab_keywordButton_actionAdapter
    implements ActionListener {
  private SearchTab adaptee;
  SearchTab_keywordButton_actionAdapter(SearchTab adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.labelButton_actionPerformed(e);
  }
}

class SearchTab_labelButton_actionAdapter
    implements ActionListener {
  private SearchTab adaptee;
  SearchTab_labelButton_actionAdapter(SearchTab adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.detailsButton_actionPerformed(e);
  }
}
