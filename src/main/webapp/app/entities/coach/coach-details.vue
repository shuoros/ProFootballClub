<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="coach">
        <h2 class="jh-entity-heading" data-cy="coachDetailsHeading">
          <span v-text="$t('proFootballClubApp.coach.detail.title')">Coach</span> {{ coach.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.coach.name')">Name</span>
          </dt>
          <dd>
            <span>{{ coach.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.coach.banned')">Banned</span>
          </dt>
          <dd>
            <span>{{ coach.banned }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.coach.abandoned')">Abandoned</span>
          </dt>
          <dd>
            <span>{{ coach.abandoned }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.coach.subscribed')">Subscribed</span>
          </dt>
          <dd>
            <span>{{ coach.subscribed }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.coach.plan')">Plan</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.Plan.' + coach.plan)">{{ coach.plan }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.coach.user')">User</span>
          </dt>
          <dd>
            {{ coach.user ? coach.user.id : '' }}
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="coach.id" :to="{ name: 'CoachEdit', params: { coachId: coach.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./coach-details.component.ts"></script>
