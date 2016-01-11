package org.kadyrov.todo.swing.view;

import org.kadyrov.todo.dao.api.domain.Todo;
import org.kadyrov.todo.swing.controller.TodoController;
import org.kadyrov.todo.swing.model.TodoListModel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TodoView extends JFrame {

    private static Logger logger = Logger.getLogger(TodoView.class.getName());
    {
        logger.setLevel(Level.ALL);
    }

    public static final String TODO_APPLICATION = "Todo application";
    final JButton loadAllBtn = new JButton("Load all");
    final JButton addBtn = new JButton("New");
    final JButton editBtn = new JButton("Edit");
    final JButton removeBtn = new JButton("Remove");
    final JTextField todoName = new JTextField(30);
    final JList<Todo> list = new JList<>();
    JLabel statusLabel = new JLabel("Start");
    TodoController controller;

    public void setController(TodoController controller) {
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

    public void cleanTodoName() {
        todoName.setText("");
    }

    public void updateModel(TodoListModel listModel) {
        list.setModel(listModel);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, TODO_APPLICATION, JOptionPane.ERROR_MESSAGE);
    }

    public void showInfo(String message) {
        statusLabel.setText(message);
    }

    public boolean confirm(String question) {
        return JOptionPane.showConfirmDialog(this, question, TODO_APPLICATION, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION;
    }

    public void init() {
        logger.config("Start init ui");
        setTitle(TODO_APPLICATION);
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
                showCreateTodoDialog(list.getSelectedValue());
            }
        });

        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCreateTodoDialog(list.getSelectedValue());
            }
        });

        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.removeTodo(list.getSelectedValue());
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

    public void showCreateTodoDialog(final Todo selectedValue){
        final JDialog createTodoForm = new JDialog(this, "Create new Todo", Dialog.ModalityType.APPLICATION_MODAL);
        JPanel content = new JPanel(new GridLayout(3, 1));
        final JPanel namePanel = new JPanel(new FlowLayout());
        namePanel.add(new JLabel("Name:"));
        final JTextField todoName = new JTextField(selectedValue==null? "":selectedValue.getName(),30);
        namePanel.add(todoName);
        content.add(namePanel);


        final JPanel descPanel = new JPanel(new FlowLayout());
        descPanel.add(new JLabel("Description:"));
        final JTextArea todoDesc = new JTextArea(selectedValue==null? "":selectedValue.getDescription(),5, 30);
        descPanel.add(todoDesc);
        content.add(descPanel);

        JPanel btnPanel = new JPanel(new FlowLayout());

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTodoForm.dispose();
            }
        });
        btnPanel.add(cancel);

        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedValue==null){
                    Todo todo = new Todo(todoName.getText());
                    todo.setDescription(todoDesc.getText());
                    logger.info("Create todo: "+todo);
                    controller.addTodo(todo);
                } else {
                    selectedValue.setName(todoName.getText());
                    selectedValue.setDescription(todoDesc.getText());
                    logger.info("Update todo: "+selectedValue);
                    controller.editTodo(selectedValue);
                }
                createTodoForm.dispose();
            }
        });
        btnPanel.add(save);

        content.add(btnPanel);

        createTodoForm.getContentPane().add(content);
        createTodoForm.setLocationRelativeTo(null);
        createTodoForm.pack();
        createTodoForm.setVisible(true);
    }


}
