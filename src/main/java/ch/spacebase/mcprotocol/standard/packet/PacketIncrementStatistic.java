package ch.spacebase.mcprotocol.standard.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ch.spacebase.mcprotocol.net.Client;
import ch.spacebase.mcprotocol.net.ServerConnection;
import ch.spacebase.mcprotocol.packet.Packet;

public class PacketIncrementStatistic extends Packet {

	public int statistic;
	public int amount;

	public PacketIncrementStatistic() {
	}

	public PacketIncrementStatistic(int statistic, int amount) {
		this.statistic = statistic;
		this.amount = amount;
	}

	@Override
	public void read(DataInputStream in) throws IOException {
		this.statistic = in.readInt();
		this.amount = in.readInt();
	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		out.writeInt(this.statistic);
		out.writeInt(this.amount);
	}

	@Override
	public void handleClient(Client conn) {
	}

	@Override
	public void handleServer(ServerConnection conn) {
	}

	@Override
	public int getId() {
		return 200;
	}

}
