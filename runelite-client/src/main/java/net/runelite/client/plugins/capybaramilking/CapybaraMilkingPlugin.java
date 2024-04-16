/*
 * Copyright (c) 2024, odlmb7 <https://github.com/odlmb7>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.capybaramilking;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.AnimationID;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@PluginDescriptor(
	name = "Capybara Milking",
	description = "Fixes the egregious oversight by Jagex to now allow players to milk capybaras",
	tags = {"capybara", "milk", "luqmpuz"}
)
@Slf4j
public class CapybaraMilkingPlugin extends Plugin
{
	@Inject
	private Client client;

	private Integer capybaraId = null;

	@Override
	public void startUp()
	{

	}

	@Override
	public void shutDown()
	{

	}

	@Subscribe
	public void onGameTick()
	{
		if (capybaraId == null)
		{
			return;
		}
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		final NPC npc = event.getMenuEntry().getNpc();

		if (npc == null || npc.getId() != 12772)
		{
			return;
		}

		capybaraId = event.getMenuEntry().getNpc().getId();
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		final NPC npc = event.getMenuEntry().getNpc();
		if (npc == null || npc.getId() != 12772)//npc.getId() < NpcID.CAPYBARA || npc.getId() > NpcID.CAPYBARA_12971)
		{
			return;
		}

		client.createMenuEntry(0)
			.setOption("Milk")
			.setTarget(event.getTarget())
			.setType(MenuAction.ITEM_USE_ON_NPC)
			.setIdentifier(event.getIdentifier())
			.onClick(e ->
			{
				client.getLocalPlayer().setAnimation(AnimationID.MILKING_COW);
				client.getLocalPlayer().setAnimationFrame(0);
				log.info("Successfully Milked");
			});
	}
}
