<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="message">
        <h2 class="jh-entity-heading" data-cy="messageDetailsHeading">
          <span v-text="$t('proFootballClubApp.message.detail.title')">Message</span> {{ message.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.message.message')">Message</span>
          </dt>
          <dd>
            <span>{{ message.message }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.message.coach')">Coach</span>
          </dt>
          <dd>
            <div v-if="message.coach">
              <router-link :to="{ name: 'TeamView', params: { teamId: message.coach.id } }">{{ message.coach.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.message.ticket')">Ticket</span>
          </dt>
          <dd>
            <div v-if="message.ticket">
              <router-link :to="{ name: 'TicketView', params: { ticketId: message.ticket.id } }">{{ message.ticket.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.message.privateChat')">Private Chat</span>
          </dt>
          <dd>
            <div v-if="message.privateChat">
              <router-link :to="{ name: 'PrivateChatView', params: { privateChatId: message.privateChat.id } }">{{
                message.privateChat.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.message.publicChat')">Public Chat</span>
          </dt>
          <dd>
            <div v-if="message.publicChat">
              <router-link :to="{ name: 'PublicChatView', params: { publicChatId: message.publicChat.id } }">{{
                message.publicChat.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="message.id" :to="{ name: 'MessageEdit', params: { messageId: message.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./message-details.component.ts"></script>
