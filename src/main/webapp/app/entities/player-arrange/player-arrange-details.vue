<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="playerArrange">
        <h2 class="jh-entity-heading" data-cy="playerArrangeDetailsHeading">
          <span v-text="$t('proFootballClubApp.playerArrange.detail.title')">PlayerArrange</span> {{ playerArrange.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.playerArrange.post')">Post</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.Post.' + playerArrange.post)">{{ playerArrange.post }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.playerArrange.player')">Player</span>
          </dt>
          <dd>
            <div v-if="playerArrange.player">
              <router-link :to="{ name: 'PlayerView', params: { playerId: playerArrange.player.id } }">{{
                playerArrange.player.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.playerArrange.composition')">Composition</span>
          </dt>
          <dd>
            <div v-if="playerArrange.composition">
              <router-link :to="{ name: 'CompositionView', params: { compositionId: playerArrange.composition.id } }">{{
                playerArrange.composition.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="playerArrange.id"
          :to="{ name: 'PlayerArrangeEdit', params: { playerArrangeId: playerArrange.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./player-arrange-details.component.ts"></script>
