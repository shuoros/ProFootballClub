<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="friendRequest">
        <h2 class="jh-entity-heading" data-cy="friendRequestDetailsHeading">
          <span v-text="$t('proFootballClubApp.friendRequest.detail.title')">FriendRequest</span> {{ friendRequest.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.friendRequest.from')">From</span>
          </dt>
          <dd>
            <div v-if="friendRequest.from">
              <router-link :to="{ name: 'TeamView', params: { teamId: friendRequest.from.id } }">{{ friendRequest.from.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.friendRequest.tu')">Tu</span>
          </dt>
          <dd>
            <div v-if="friendRequest.tu">
              <router-link :to="{ name: 'TeamView', params: { teamId: friendRequest.tu.id } }">{{ friendRequest.tu.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="friendRequest.id"
          :to="{ name: 'FriendRequestEdit', params: { friendRequestId: friendRequest.id } }"
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

<script lang="ts" src="./friend-request-details.component.ts"></script>
