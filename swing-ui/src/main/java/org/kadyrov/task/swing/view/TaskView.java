package org.kadyrov.task.swing.view;

import org.kadyrov.task.dao.api.domain.Task;
import org.kadyrov.task.swing.controller.TaskController;
import org.kadyrov.task.swing.model.TaskListModel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is simple view implementation
 */
public class TaskView extends JFrame {

    private static Logger logger = Logger.getLogger(TaskView.class.getName());
    static {
        logger.setLevel(Level.ALL);
    }

    public static final String task_APPLICATION = "Task application";
    final JButton loadAllBtn = new JButton("Load all");
    final JButton addBtn = new JButton("New");
    final JButton editBtn = new JButton("Edit");
    final JButton removeBtn = new JButton("Remove");
    final JTextField taskName = new JTextField(30);
    final JList<Task> list = new JList<>();
    JLabel statusLabel = new JLabel("Start");
    TaskController controller;

    public void setController(TaskController controller) {
        this.controller = controller;
    }

    public void disableRemoveBtn() {
        removeBtn.setEnabled(false);
        editBtn.setEnabled(false);
    }

    public void enableRemoveBtn() {
        removeBtn.setEnabled(true);
        editBtn.setEnabled(true);
    }

    public void cleanTaskName() {
        taskName.setText("");
    }

    public void updateModel(TaskListModel listModel) {
        list.setModel(listModel);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, task_APPLICATION, JOptionPane.ERROR_MESSAGE);
    }

    public void showInfo(String message) {
        statusLabel.setText(message);
    }

    public boolean confirm(String question) {
        return JOptionPane.showConfirmDialog(this, question, task_APPLICATION, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION;
    }

    public void init() {
        logger.config("Start init ui");
        setTitle(task_APPLICATION);
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);
        setPreferredSize(new Dimension(600, 400));
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                enableRemoveBtn();
            }
        });

        removeBtn.setEnabled(false);
        editBtn.setEnabled(false);

        loadAllBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.loadAll();
            }
        });

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTaskDialog(list.getSelectedValue());
            }
        });

        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTaskDialog(list.getSelectedValue());
            }
        });

        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.removeTask(list.getSelectedValue());
            }
        });

        JPanel buttonsPanel = new JPanel();

        buttonsPanel.add(loadAllBtn);
        buttonsPanel.add(addBtn);
        buttonsPanel.add(editBtn);
        buttonsPanel.add(removeBtn);


        topPanel.add(buttonsPanel);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panel.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(panel.getWidth(), 16));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);

        setLocationRelativeTo(null);

        pack();
        logger.config("Completed init ui");
    }

    public void showTaskDialog(final Task selectedValue){

        final JDialog taskForm = new JDialog(this, "Create new task", Dialog.ModalityType.APPLICATION_MODAL);
        JPanel content = new JPanel(new GridLayout(3, 1));
        final JPanel namePanel = new JPanel(new FlowLayout());
        namePanel.add(new JLabel("Name:"));
        final JTextField taskName = new JTextField(selectedValue==null? "":selectedValue.getName(),30);
        namePanel.add(taskName);
        content.add(namePanel);


        final JPanel descPanel = new JPanel(new FlowLayout());
        descPanel.add(new JLabel("Description:"));
        final JTextArea taskDesc = new JTextArea(selectedValue==null? "":selectedValue.getDescription(),5, 30);
        descPanel.add(taskDesc);
        content.add(descPanel);

        JPanel btnPanel = new JPanel(new FlowLayout());

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskForm.dispose();
            }
        });
        btnPanel.add(cancel);

        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedValue==null){
                    Task task = new Task(taskName.getText());
                    task.setDescription(taskDesc.getText());
                    logger.info("Create task: "+task);
                    controller.addTask(task);
                } else {
                    selectedValue.setName(taskName.getText());
                    selectedValue.setDescription(taskDesc.getText());
                    logger.info("Update task: "+selectedValue);
                    controller.editTask(selectedValue);
                }
                taskForm.dispose();
            }
        });
        btnPanel.add(save);

        content.add(btnPanel);

        taskForm.getContentPane().add(content);
        taskForm.setLocationRelativeTo(null);
        taskForm.pack();
        taskForm.setVisible(true);
    }


}
