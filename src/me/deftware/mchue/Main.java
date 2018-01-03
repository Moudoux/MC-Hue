package me.deftware.mchue;

import lombok.Getter;
import me.deftware.client.framework.Client.EMCClient;
import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Event.Events.EventClientCommand;
import me.deftware.client.framework.Wrappers.IChat;

public class Main extends EMCClient {

	@Getter
	private HueBridge bridge;

	@Getter
	private static Main instance;

	@Override
	public void initialize() {
		instance = this;
		bridge = new HueBridge();
	}

	@Override
	public EMCClientInfo getClientInfo() {
		return new EMCClientInfo("MC-Hue", "1");
	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof EventClientCommand) {
			EventClientCommand cmd = (EventClientCommand) event;
			if (cmd.getCommand().equals(".hue")) {
				event.setCanceled(true);
				if (!cmd.getArgs().contains(" ") || cmd.getArgs().split(" ").length < 3) {
					IChat.sendClientMessage("§cInvalid command arguments");
					return;
				}
				String command = cmd.getArgs().split(" ")[0];
				if (command.equals("color")) {
					int id = Integer.valueOf(cmd.getArgs().split(" ")[1]);
					int hue = Integer.valueOf(cmd.getArgs().split(" ")[2]);
					bridge.setColor(hue, id);
					IChat.sendClientMessage("§aSet light " + String.valueOf(id) + " to hue \"" + hue + "\"");
				}
			}
		}
	}

}
