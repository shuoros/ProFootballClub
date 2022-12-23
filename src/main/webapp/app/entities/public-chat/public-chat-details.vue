<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="publicChat">
        <h2 class="jh-entity-heading" data-cy="publicChatDetailsHeading">
          <span v-text="$t('proFootballClubApp.publicChat.detail.title')">PublicChat</span> {{ publicChat.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.publicChat.from')">From</span>
          </dt>
          <dd>
            <div v-if="publicChat.from">
              <router-link :to="{ name: 'TeamView', params: { teamId: publicChat.from.id } }">{{ publicChat.from.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.publicChat.tu')">Tu</span>
          </dt>
          <dd>
            <div v-if="publicChat.tu">
              <router-link :to="{ name: 'TeamView', params: { teamId: publicChat.tu.id } }">{{ publicChat.tu.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="publicChat.id"
          :to="{ name: 'PublicChatEdit', params: { publicChatId: publicChat.id } }"
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

<script lang="ts" src="./public-chat-details.component.ts"></script>
