<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.ticket.home.createOrEditLabel"
          data-cy="TicketCreateUpdateHeading"
          v-text="$t('proFootballClubApp.ticket.home.createOrEditLabel')"
        >
          Create or edit a Ticket
        </h2>
        <div>
          <div class="form-group" v-if="ticket.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="ticket.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.ticket.subject')" for="ticket-subject">Subject</label>
            <input
              type="text"
              class="form-control"
              name="subject"
              id="ticket-subject"
              data-cy="subject"
              :class="{ valid: !$v.ticket.subject.$invalid, invalid: $v.ticket.subject.$invalid }"
              v-model="$v.ticket.subject.$model"
              required
            />
            <div v-if="$v.ticket.subject.$anyDirty && $v.ticket.subject.$invalid">
              <small class="form-text text-danger" v-if="!$v.ticket.subject.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.ticket.coach')" for="ticket-coach">Coach</label>
            <select class="form-control" id="ticket-coach" data-cy="coach" name="coach" v-model="ticket.coach">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="ticket.coach && teamOption.id === ticket.coach.id ? ticket.coach : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.ticket.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./ticket-update.component.ts"></script>
