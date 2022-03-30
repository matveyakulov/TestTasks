package com.matveyakulov.github.task3;

import java.net.InetSocketAddress;
import java.util.List;

public class Task {

    void processTask(ChannelHandlerContext ctx) {

        InetSocketAddress lineAddress = new InetSocketAddress(getIpAddress(), getUdpPort());
        List<Comand> commands = getAllCommands();
        for (Command currentCommand : commands) {
            CommandType commandType = currentCommand.getCommandType();
            if (!currentCommand.isAttemptsNumberExhausted()) {
                sendCommandToContext(ctx, lineAddress, currentCommand.getCommandText());

                try {
                    AdminController.getInstance().processUssdMessage(
                            new DblIncomeUssdMessage(lineAddress.getHostName(),
                                    lineAddress.getPort(),
                                    0,
                                    EnumGoip.getByModel(getGoipModel()),
                                    currentCommand.getCommandText()),
                            false);
                } catch (Exception ignored) {
                }
                if (commandType == CommandType.REBOOT_CHANNEL && currentCommand.isTimeToSend()) {
                    currentCommand.setSendDate(new Date());
                }
                Log.ussd.write(String.format("sent: ip: %s; порт: %d; %s",
                        lineAddress.getHostString(),
                        lineAddress.getPort(),
                        currentCommand.getCommandText()));
                currentCommand.incSendCounter();

            } else {
                deleteCommand(commandType);
            }
        }
        sendKeepAliveOkAndFlush(ctx);
    }

    void processTask1(ChannelHandlerContext ctx) {
        InetSocketAddress lineAddress = new InetSocketAddress(getIpAddress(), getUdpPort());
        CommandType typeToRemove;
        for (Command currentCommand : getAllCommands()) {
            if (currentCommand.getCommandType() == CommandType.REBOOT_CHANNEL) {
                if (!currentCommand.isAttemptsNumberExhausted()) {
                    if (currentCommand.isTimeToSend()) {
                        sendCommandToContext(ctx, lineAddress, currentCommand.getCommandText());

                        try {
                            AdminController.getInstance().processUssdMessage(
                                    new DblIncomeUssdMessage(lineAddress.getHostName(), lineAddress.getPort(), 0,
                                            EnumGoip.getByModel(getGoipModel()), currentCommand.getCommandText()), false);
                        } catch (Exception ignored) {
                        }

                        currentCommand.setSendDate(new Date());
                        Log.ussd.write(String.format("sent: ip: %s; порт: %d; %s",
                                lineAddress.getHostString(), lineAddress.getPort(), currentCommand.getCommandText()));
                        currentCommand.incSendCounter();
                    }
                } else {
                    typeToRemove = currentCommand.getCommandType();
                    deleteCommand(typeToRemove);
                }
            } else {
                if (!currentCommand.isAttemptsNumberExhausted()) {
                    sendCommandToContext(ctx, lineAddress, currentCommand.getCommandText());

                    try {
                        AdminController.getInstance().processUssdMessage(
                                new DblIncomeUssdMessage(lineAddress.getHostName(), lineAddress.getPort(), 0,
                                        EnumGoip.getByModel(getGoipModel()), currentCommand.getCommandText()), false);
                    } catch (Exception ignored) {
                    }

                    Log.ussd.write(String.format("sent: ip: %s; порт: %d; %s",
                            lineAddress.getHostString(), lineAddress.getPort(), currentCommand.getCommandText()));
                    currentCommand.setSendDate(new Date());
                    currentCommand.incSendCounter();
                } else {
                    typeToRemove = currentCommand.getCommandType();
                    deleteCommand(typeToRemove);
                }
            }
        }
        sendKeepAliveOkAndFlush(ctx);
    }


}
