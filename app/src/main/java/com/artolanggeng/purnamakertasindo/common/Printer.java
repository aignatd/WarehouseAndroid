package com.artolanggeng.purnamakertasindo.common;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.Preference;
import com.starmicronics.stario.PortInfo;
import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Printer extends AppCompatActivity
{
	@BindView(R.id.etPortName)
	EditText etPortName;
	@BindView(R.id.spPrinter)
	Spinner spPrinter;

	static String TAG = "[Pemasok]";
	private Context context = this;
	Calendar dateandtime;

	List<EditText> lstInput = new ArrayList<>();
	List<String> lstMsg = new ArrayList<>();

	private PopupMessege popupMessege = new PopupMessege();
	static ProgressDialog progressDialog;

	private String strInterface = "";
	private static ArrayList<byte[]> list = new ArrayList<>();

	final ArrayList<PortInfo> arrayDiscovery = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_printer);
		ButterKnife.bind(this);
		etPortName.setText(Fungsi.getStringFromSharedPref(context, Preference.prefPortName));
	}

	@OnClick({R.id.ivBackIcon, R.id.ivCariPrinter, R.id.tvTestPrinter})
	public void onViewClicked(View view)
	{
		switch(view.getId())
		{
			case R.id.ivBackIcon:
				BackActivity();
			break;
			case R.id.ivCariPrinter:
				if(spPrinter.getSelectedItemPosition() == 0)
					StarPortDiscovery();
			break;
			case R.id.tvTestPrinter:
				list.clear();

				// 1D barcode example
				list.add(new byte[] { 0x1b, 0x1d, 0x61, 0x01 }); // Center in paper
				list.add(new byte[] { 0x1b, 0x62, 0x06, 0x02, 0x02 });

				list.add(" 12ab34cd56\u001e\r\n".getBytes());

				Runnable runnable = new Runnable()
				{
					@Override
					public void run()
					{
						Integer intHasil = Fungsi.sendCommandStar(context, etPortName.getText().toString(), "", list);
						Log.d(TAG, "run: " + intHasil);
					}
				};

				new Thread(runnable).start();
			break;
		}
	}

	private void BackActivity()
	{
		Intent TimbanganIntent = new Intent(Printer.this, Timbangan.class);
		startActivity(TimbanganIntent);
		finish();
	}

	@Override
	public void onBackPressed()
	{
		BackActivity();
	}

	public void StarPortDiscovery()
	{
		final String item_list[] = new String[]{"LAN", "Bluetooth", "USB", "All",};

		strInterface = "LAN";

		AlertDialog.Builder portDiscoveryDialog = new AlertDialog.Builder(this);
		portDiscoveryDialog.setIcon(android.R.drawable.checkbox_on_background);
		portDiscoveryDialog.setTitle("Port Discovery List");
		portDiscoveryDialog.setCancelable(false);
		portDiscoveryDialog.setSingleChoiceItems(item_list, 0, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				strInterface = item_list[whichButton];
			}
		});

		portDiscoveryDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
				((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE).setEnabled(false);

				progressDialog = ProgressDialog.show(context, getResources().getString(R.string.msgHarapTunggu),
					context.getResources().getString(R.string.msgDaftarPrinter));
				progressDialog.setCancelable(false);

				new Thread(new Runnable()
				{
					public void run()
					{
						ArrayList<String> arrPortName = new ArrayList<>();
						arrPortName.clear();

						if(true == strInterface.equals("LAN"))
							arrPortName = getPortDiscovery("LAN");
						else if(strInterface.equals("Bluetooth"))
							arrPortName = getPortDiscovery("Bluetooth");
						else if(strInterface.equals("USB"))
							arrPortName = getPortDiscovery("USB");
						else
							arrPortName = getPortDiscovery("All");

						final ArrayList<String> arrayPortName = arrPortName;

						progressDialog.dismiss();

						runOnUiThread(new Runnable()
						{
							@Override
							public void run()
							{
								final EditText editPortName = new EditText(context);

								new AlertDialog.Builder(context).setIcon(android.R.drawable.checkbox_on_background).setTitle(context.getString(R.string.titleIPAddressPrinter)).setCancelable(false).setView(editPortName).setPositiveButton("OK", new DialogInterface.OnClickListener()
								{
									public void onClick(DialogInterface dialog, int button)
									{
										((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
										((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE).setEnabled(false);
										EditText portNameField = (EditText) findViewById(R.id.etPortName);
										portNameField.setText(editPortName.getText());
										Fungsi.storeToSharedPref(context, portNameField.getText().toString(), Preference.prefPortName);
									}
								}).setNegativeButton("Batal", new DialogInterface.OnClickListener()
								{
									public void onClick(DialogInterface dialog, int button)
									{
									}
								}).setItems(arrayPortName.toArray(new String[0]), new DialogInterface.OnClickListener()
								{
									public void onClick(DialogInterface dialog, int select)
									{
										EditText portNameField = (EditText) findViewById(R.id.etPortName);
										portNameField.setText(arrayDiscovery.get(select).getPortName());
										Fungsi.storeToSharedPref(context, portNameField.getText().toString(), Preference.prefPortName);
									}
								}).show();
							}
						});
					}
				}).start();
			}
		});
		portDiscoveryDialog.setNegativeButton("Batal", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				//
			}
		});

		portDiscoveryDialog.show();
	}

	private ArrayList<String> getPortDiscovery(String interfaceName)
	{
		List<PortInfo> BTPortList;
		List<PortInfo> TCPPortList;
		List<PortInfo> USBPortList;
		final EditText editPortName;

		arrayDiscovery.clear();

		ArrayList<String> arrayPortName = new ArrayList<>();
		arrayPortName.clear();

		try
		{
			if(true == interfaceName.equals("Bluetooth") || true == interfaceName.equals("All"))
			{
				BTPortList = StarIOPort.searchPrinter("BT:");

				for(PortInfo portInfo : BTPortList)
				{
					arrayDiscovery.add(portInfo);
				}
			}

			if(true == interfaceName.equals("LAN") || true == interfaceName.equals("All"))
			{
				TCPPortList = StarIOPort.searchPrinter("TCP:");

				for(PortInfo portInfo : TCPPortList)
				{
					arrayDiscovery.add(portInfo);
				}
			}

			if(true == interfaceName.equals("USB") || true == interfaceName.equals("All"))
			{
				USBPortList = StarIOPort.searchPrinter("USB:", this);

				for(PortInfo portInfo : USBPortList)
				{
					arrayDiscovery.add(portInfo);
				}
			}

			arrayPortName = new ArrayList<>();

			for(PortInfo discovery : arrayDiscovery)
			{
				String portName;
				portName = discovery.getPortName();

				if(discovery.getMacAddress().equals("") == false)
				{
					portName += "\n - " + discovery.getMacAddress();
					if(discovery.getModelName().equals("") == false)
					{
						portName += "\n - " + discovery.getModelName();
					}
				} else if(interfaceName.equals("USB") || interfaceName.equals("All"))
				{
					if(!discovery.getModelName().equals(""))
					{
						portName += "\n - " + discovery.getModelName();
					}
					if(!discovery.getUSBSerialNumber().equals(" SN:"))
					{
						portName += "\n - " + discovery.getUSBSerialNumber();
					}
				}

				arrayPortName.add(portName);
			}
		}
		catch(StarIOPortException e)
		{
			// e.printStackTrace();
		}

		return arrayPortName;
	}
}
