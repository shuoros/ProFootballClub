<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="ticket">
        <h2 class="jh-entity-heading" data-cy="ticketDetailsHeading">
          <span v-text="$t('proFootballClubApp.ticket.detail.title')">Ticket</span> {{ ticket.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.ticket.subject')">Subject</span>
          </dt>
          <dd>
            <span>{{ ticket.subject }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.ticket.coach')">Coach</span>
          </dt>
          <dd>
            <div v-if="ticket.coach">
              <router-link :to="{ name: 'TeamView', params: { teamId: ticket.coach.id } }">{{ ticket.coach.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="ticket.id" :to="{ name: 'TicketEdit', params: { ticketId: ticket.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./ticket-details.component.ts"></script>
