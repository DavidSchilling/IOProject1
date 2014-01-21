package io.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import io.controller.IOController;
import io.model.Game;

@SuppressWarnings("serial")
public class IOPanel extends JPanel
{
	private JButton saveButton;
	private JButton loadButton;
	private JTextField titleField;
	private JLabel titleLabel;
	private JTextField rankingField;
	private JLabel rankingLabel;
	private JTextArea rulesArea;
	private JLabel rulesLabel;
	private SpringLayout baseLayout;
	private IOController baseController;
	private JLabel gameCountLabel;

	public IOPanel(IOController baseController)
	{
		this.baseController = baseController;

		saveButton = new JButton("SAVE");
		loadButton = new JButton("LOAD");
		titleField = new JTextField(15);
		titleLabel = new JLabel("GameTitle: ");
		rankingField = new JTextField(5);
		rankingLabel = new JLabel("Rank: ");
		rulesArea = new JTextArea(5, 20);
		rulesLabel = new JLabel("Game Rules: ");
		baseLayout = new SpringLayout();

		gameCountLabel = new JLabel("Number of Games: ");

		setupPanel();
		setupLayout();
		setupListeners();
	}

	private void setupPanel()
	{
		this.setLayout(baseLayout);
		this.setBackground(Color.lightGray);
		this.add(rankingField);
		this.add(rankingLabel);
		this.add(rulesArea);
		this.add(rulesLabel);
		this.add(saveButton);
		this.add(titleField);
		this.add(titleLabel);
		this.add(gameCountLabel);
		this.add(loadButton);

	}

	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.NORTH, loadButton, 20, SpringLayout.SOUTH, rulesArea);
		baseLayout.putConstraint(SpringLayout.NORTH, saveButton, 0, SpringLayout.NORTH, loadButton);
		baseLayout.putConstraint(SpringLayout.EAST, loadButton, 0, SpringLayout.EAST, titleField);
		baseLayout.putConstraint(SpringLayout.NORTH, rulesArea, -5, SpringLayout.NORTH, rulesLabel);
		baseLayout.putConstraint(SpringLayout.WEST, rulesArea, 0, SpringLayout.WEST, rankingField);
		baseLayout.putConstraint(SpringLayout.WEST, rankingField, 35, SpringLayout.EAST,
				rankingLabel);
		baseLayout.putConstraint(SpringLayout.NORTH, rulesLabel, 23, SpringLayout.SOUTH,
				rankingLabel);
		baseLayout.putConstraint(SpringLayout.WEST, rulesLabel, 0, SpringLayout.WEST, rankingLabel);
		baseLayout.putConstraint(SpringLayout.WEST, titleField, 0, SpringLayout.WEST, rankingField);
		baseLayout.putConstraint(SpringLayout.SOUTH, titleField, -6, SpringLayout.NORTH,
				rankingField);
		baseLayout.putConstraint(SpringLayout.NORTH, rankingField, -3, SpringLayout.NORTH,
				rankingLabel);
		baseLayout.putConstraint(SpringLayout.NORTH, rankingLabel, 14, SpringLayout.SOUTH,
				titleLabel);
		baseLayout.putConstraint(SpringLayout.SOUTH, titleLabel, -228, SpringLayout.SOUTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, rankingLabel, 0, SpringLayout.WEST, titleLabel);
		baseLayout.putConstraint(SpringLayout.WEST, titleLabel, 68, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.EAST, saveButton, 0, SpringLayout.EAST,
				gameCountLabel);
		baseLayout.putConstraint(SpringLayout.WEST, gameCountLabel, 68, SpringLayout.WEST, this);
		baseLayout
				.putConstraint(SpringLayout.SOUTH, gameCountLabel, -271, SpringLayout.SOUTH, this);

	}

	private void setupListeners()
	{

		saveButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent click)
			{
				Game tempGame = baseController.makeGameFromInput(titleField.getText(),
						rankingField.getText(), rulesArea.getText());
				if (tempGame != null)
					{
						baseController.saveGameInformation(tempGame);
						gameCountLabel.setText("Number of Games: " +
						baseController.getProjectGames().size());
					} else
					{
						JOptionPane.showMessageDialog(null, "Not a valid number");
					}
			}

		});

		loadButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				Game tempGame = baseController.readGameInformation();
				if (tempGame != null)
					{
						titleField.setText(tempGame.getGameTitle());
						rankingField.setText(Integer.toString((tempGame.getFunRanking())));
						String tempRules = "";
						for (String currentRule : tempGame.getGameRules())
							{
								tempRules += currentRule + "\n";
							}

						rulesArea.setText(tempRules);

					} else
					{
						JOptionPane.showMessageDialog(null,
								"Check the file, make sure it is in order.");
					}

			}

		});

	}
}
