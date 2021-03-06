/*
 * Copyright 2016 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.loader.mixin.server;

import net.fabricmc.loader.FabricLoader;
import net.fabricmc.loader.mixin.hooks.IServerGetFile;
import net.fabricmc.loader.server.ServerSidedHandler;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.IOException;

@Mixin(MinecraftDedicatedServer.class)
public abstract class MixinDedicatedServer {
	@Inject(method = "setupServer", at = @At("HEAD"))
	public void setupServer(CallbackInfoReturnable<Boolean> info) throws IOException {
		FabricLoader.INSTANCE.initialize(((IServerGetFile) (Object) this).fabricHookGetFile(""), new ServerSidedHandler((MinecraftDedicatedServer) (Object) this));
		FabricLoader.INSTANCE.load(((IServerGetFile) (Object) this).fabricHookGetFile("mods"));
	}
}
