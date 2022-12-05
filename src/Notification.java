import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import com.notification.NotificationBuilder;
import com.notification.types.BorderLayoutNotification;
import com.theme.TextTheme;
import com.theme.ThemePackage;
import com.theme.WindowTheme;

// Custom system notification utilizing the open source library found here:
// https://github.com/spfrommer/JCommunique
public class Notification extends BorderLayoutNotification
{
	private JLabel m_label, i_label;
	private JButton m_button;
	private TextTheme m_theme;

	public Notification()
	{
		m_label = new JLabel();
		i_label = new JLabel();
		m_button = new JButton("Ok");

		m_button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				removeComponent(m_button);
				hide();
			}
		});

		this.addComponent(m_label, BorderLayout.NORTH);
		this.addComponent(i_label, BorderLayout.CENTER);
		this.addComponent(m_button, BorderLayout.SOUTH);
	}

	// This will set the title and subtitle font and color.
	public void setTextTheme(TextTheme theme)
	{
		m_label.setFont(theme.title);
		m_label.setForeground(theme.titleColor);
		i_label.setFont(theme.subtitle);
		i_label.setForeground(theme.subtitleColor);
		m_button.setFont(theme.subtitle);
		m_button.setForeground(theme.subtitleColor);

		m_theme = theme;
	}

	public String getTitle()
	{
		return m_label.getText();
	}

	public String getMsg()
	{
		return i_label.getText();
	}

	public void setText(String title, String msg)
	{
		m_label.setText(title);
		i_label.setText(msg);
	}

	@Override
	public void setWindowTheme(WindowTheme theme)
	{
		super.setWindowTheme(theme);

		if (m_theme != null)
		{
			m_label.setForeground(m_theme.titleColor);
			i_label.setForeground(m_theme.subtitleColor);
			m_button.setForeground(m_theme.subtitleColor);
		}
	}

	public static class CustomBuilder implements NotificationBuilder<Notification>
	{
		@Override
		public Notification buildNotification(ThemePackage pack, Object... args)
		{
			Notification note = new Notification();
			note.setWindowTheme(pack.getTheme(WindowTheme.class));
			note.setTextTheme(pack.getTheme(TextTheme.class));

			if (args.length > 0)
			{
				note.setText((String) args[0], (String) args[1]);
				note.m_label.setForeground((Color) args[2]);
			}
			else
			{
				note.setText("Error", "No text supplied");
			}
			return note;
		}
	}
}