<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="privateChat">
        <h2 class="jh-entity-heading" data-cy="privateChatDetailsHeading">
          <span v-text="$t('proFootballClubApp.privateChat.detail.title')">PrivateChat</span> {{ privateChat.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.privateChat.league')">League</span>
          </dt>
          <dd>
            <div v-if="privateChat.league">
              <router-link :to="{ name: 'LeagueView', params: { leagueId: privateChat.league.id } }">{{
                privateChat.league.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.privateChat.cup')">Cup</span>
          </dt>
          <dd>
            <div v-if="privateChat.cup">
              <router-link :to="{ name: 'CupView', params: { cupId: privateChat.cup.id } }">{{ privateChat.cup.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="privateChat.id"
          :to="{ name: 'PrivateChatEdit', params: { privateChatId: privateChat.id } }"
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

<script lang="ts" src="./private-chat-details.component.ts"></script>
